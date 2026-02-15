---
name: unit-test-generate 
description: 'ユニットテストを生成するためのプロンプトです。クリーンアーキテクチャの各レイヤーに応じた検証観点と命名規則、記述スタイルに従ってテストコードを作成します。'
agent: agent
---

あなたはクリーンアーキテクチャを採用したプロジェクトの品質管理責任者（QA Lead）です。
現在アクティブになっている `プロダクトコード` に対する「単体テスト（Unit Test）」を作成してください。

以下の #プロセス と #ガイドライン に厳密に従って作業を行ってください。

# プロセス
テストコードを出力する前に、必ず以下の「分析フェーズ」を実行し、私に確認を求めてください。

**Step 1: コンテキスト分析と設計レビュー**
1. 対象クラスの **アーキテクチャ上の責務（Layer）** を特定してください（例: Domain Model, UseCase, Interface Adapter/Repository, Controller 等）。
2. その責務に基づき、重点的にテストすべき **「検証の観点」** を列挙してください。
  - (例: Repositoryなら「Entityへの詰め替え」、UseCaseなら「フローと例外」、Domainなら「バリデーションと計算」)
3. プロダクトコードが「テストしにくい構造（依存関係が複雑、可視性が不適切など）」になっている場合は、テストを書く前にリファクタリング案を提示してください。

**Step 2: 実装（Step 1の合意後）**
- 私が「OK」または「Go」と指示したら、以下のガイドラインに従ってテストコードを出力してください。

# ガイドライン

## 1. テクニカルスタック
- **Testing Framework**: JUnit 5
- **Mocking**: Mockito (`@ExtendWith(MockitoExtension.class)`)
- **Assertion**: AssertJ (`assertThat(...)`)

## 2. テスト構造と命名
- **グルーピング**: テスト対象メソッド単位で`@Nested` を使用して階層化する。
- **命名規則**: 日本語で記述する。
  - フォーマット: `何を_どのような状態で_どうなると_期待する結果`
  - 悪い例: `testSave`
  - 良い例: `正常なドメインモデルを渡した場合_物理エンティティに変換され保存されること`

## 3. 記述スタイル (AAAパターン)
各テストメソッド内は必ず以下の3ブロックに分け、コメントを記述すること。
```java
@Test
void テスト名() {
    // 前準備 (Arrange): モックの定義、入力データの作成
    ...
    // 実行 (Act): テスト対象メソッドの実行
    ...
    // 検証 (Assert): 結果の検証、モック呼び出しの検証(ArgumentCaptor含む)
    ...
}
```

## 4. レイヤー別検証ルールと実装テンプレート
Copilotは対象クラスのパッケージや命名からレイヤーを自動判定し、以下のルールとテンプレートを適用すること。

### 4.1 Repository (Interface Adapter / JPA Implementation)

#### 検証ルール
- **原則としてMockは使用しない**: 共通親クラス `AbstractRepositoryTest` を継承し、実コンテナ（PostgreSQL）を利用した結合テストとして実装すること。
- **実装クラスのImport**: `@DataJpaTest` はインターフェース以外を自動スキャンしないため、テスト対象の具象クラス（`@Repository`）を必ず明示的に `@Import` すること。
- **ラウンドトリップ検証**: `repository.save()` を実行後、DaoやEntityManagerを使用してDBから値を再取得し、ドメインモデルからDBエンティティへのマッピングが全フィールド正しいか検証すること。
- **マッピング検証**: DB固有の型（Enum、LocalDateTime、JSON、カスタムコンバータ等）が意図通りに永続化・復元されているかに重点を置くこと。

#### 実装テンプレート
```java
import com.example.demo.infra.database.AbstractRepositoryTest;

@Import(${TargetRepository}.class)
class ${TargetRepository}Test extends AbstractRepositoryTest {
  @Autowired
    private ${TargetRepository} repository;

    @Autowired
    private ${RelatedDao} dao;

    // テストメソッド群
}
```

### 4.2 UseCase (Application Service)
* Repositoryなどの依存コンポーネントが正しく呼ばれたか、およびビジネスルールの分岐網羅を検証すること。

### 4.3 Domain (Enterprise Business Rules)
* モックは極力使わず、純粋なインスタンス生成によるロジック検証を行うこと。
* 境界値（Boundary Value）テストを含めること。
