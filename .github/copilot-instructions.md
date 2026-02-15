# AIへの指示（Custom Instructions）

- 回答はすべて**日本語**で行ってください。
- 技術用語は無理に日本語に直さず、一般的なカタカナ表記や英語表記のままで構いません。

## レビューの指示
レビュー対象のファイル形式に応じて、以下のプロンプトを使い分けて厳守してください。

1. プログラムコード（.java, .gradle 等）のレビュー
  - 参照ファイル: [code-review-prompt.md](prompts/code-review.prompt.md)
  - 実装レベルのバグ、Java 17/Spring のベストプラクティス、コードの品質特性（複雑度等）に焦点を当ててレビューしてください。

2. 設計ドキュメント（.md, .adoc, .txt 等）のレビュー
  - 参照ファイル: [design-review.prompt.md](prompts/design-review.prompt.md)
  - アーキテクチャの妥当性、整合性、設計原則（KISS/YAGNI/DDD等）に焦点を当ててレビューしてください。

3. 共通の姿勢
  - 基本的にレビュー対象の「変更箇所」に集中してください。
  - 修正案を提示する際は、プロジェクトの技術スタック（Spring Integration, JPA, Batch等）を考慮してください。