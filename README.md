# プロジェクト名
一行で何をするプロジェクトかを書く。

## Overview
- 目的: 簡潔な目的説明
- 主な構成: apps/, libs/, docs/, infra/

## Getting Started
Prerequisites:
- OS: Linux / macOS / Windows
- 必須ツール: git, Docker (必要なら), その他

セットアップ:
1. リポジトリをクローン: `git clone <repo-url>`
2. 依存をインストール: `make setup` または `./scripts/setup.sh`
3. サービス起動（例）: `make start` または `docker-compose up`

## Development
- ビルド: `make build`
- テスト: `make test`
- Lint/Format: `make lint` / `make fmt`

## Docs
詳細設計・仕様は [docs](docs/) を参照。

## Contributing
- ブランチ命名: `feature/<ticket>`、`fix/<ticket>` 等
- PRテンプレ: タイトル / 目的 / 変更点 / 動作確認手順

## License
- ライセンス名（例: MIT）

## Contact
- Maintainer: 名前 / メール / GitHub アカウント