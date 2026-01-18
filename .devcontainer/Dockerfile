FROM mcr.microsoft.com/devcontainers/java:21-bookworm

# rootユーザーで Linuxbrew の実行に必要な依存パッケージをインストール
USER root
RUN apt-get update && apt-get install -y \
    build-essential \
    curl \
    file \
    git \
    postgresql-client \
    && rm -rf /var/lib/apt/lists/*

# 以降の作業は vscode ユーザーで行う
USER vscode
WORKDIR /home/vscode

# 1. Linuxbrew のインストール
# 非対話モード (NONINTERACTIVE=1) で実行します
RUN /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# 2. ビルドプロセス内で brew コマンドを使えるように PATH を設定
# Linuxbrew の標準的なパスを指定します
ENV PATH="/home/linuxbrew/.linuxbrew/bin:/home/linuxbrew/.linuxbrew/sbin:${PATH}"

# 3. コンテナ起動後のシェルのために .bashrc に設定を追記
# これによりターミナルを開いた時も brew コマンドが有効になります
RUN echo 'eval "$(/home/linuxbrew/.linuxbrew/bin/brew shellenv)"' >> ~/.bashrc

# 動作確認（ビルド中に brew doctor や brew --version を実行して正常か確認）
RUN brew --version

# 4. brew 経由でツールをインストール
# ビルドログで brew が依存関係を解決しながらインストールする様子が見れるはずです
RUN brew install mise direnv

# 5. mise を使って Java 17 と Gradle 8 の最新をインストール
# mise はデフォルトで最新のマイナーバージョンを探してくれます
RUN mise use --global java@17.0.2 \
    && mise use --global gradle@8.14.3

# 6. シェル設定の追記
RUN echo 'eval "$(/home/linuxbrew/.linuxbrew/bin/brew shellenv)"' >> ~/.bashrc \
    && echo 'eval "$(mise activate bash)"' >> ~/.bashrc \
    && echo 'eval "$(direnv hook bash)"' >> ~/.bashrc

# 7. Java と Gradle の HOME ディレクトリを .bashrc に書き出す
# mise where コマンドで実際のインストールパスを取得してエクスポートします
RUN echo "export JAVA_HOME=$(/home/linuxbrew/.linuxbrew/bin/mise where java)" >> ~/.bashrc \
    && echo "export GRADLE_HOME=$(/home/linuxbrew/.linuxbrew/bin/mise where gradle)" >> ~/.bashrc