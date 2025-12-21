#!/bin/bash
set -e

# --- 接続設定 ---
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-demo_db}
DB_USER=${DB_USER:-demo_user}
DB_PASS=${DB_PASS:-demo_password}

DB_URL="postgres://${DB_USER}:${DB_PASS}@${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=disable"
MIGRATE="./.bin/migrate"
SRC_DIR="./migrations"
EXE_DIR="./.run_migrations"

# 準備：隠しフォルダを作成し、サブフォルダ内のSQLをフラットにリンク
prepare_links() {
    mkdir -p "$EXE_DIR"
    rm -f "$EXE_DIR"/*
    
    # migrations 配下の全 SQL ファイルを .run_migrations へリンク
    # basename を使うことでサブフォルダ階層を無視してフラットに集約
    find "$SRC_DIR" -name "*.sql" | while read -r file; do
        ln -s "$(pwd)/$file" "$EXE_DIR/$(basename "$file")"
    done
}

case "$1" in
    "up")
        prepare_links
        $MIGRATE -path "$EXE_DIR" -database "$DB_URL" up
        ;;
    "down")
        prepare_links
        $MIGRATE -path "$EXE_DIR" -database "$DB_URL" down 1
        ;;
    "goto")
        prepare_links
        $MIGRATE -path "$EXE_DIR" -database "$DB_URL" goto "$2"
        ;;
    "version")
        prepare_links
        $MIGRATE -path "$EXE_DIR" -database "$DB_URL" version
        ;;
    "force")
        prepare_links
        $MIGRATE -path "$EXE_DIR" -database "$DB_URL" force "$2"
        ;;
    "drop")
        # ⚠️ 危険な操作なので確認を入れる
        echo "‼️  WARNING: This will DROP ALL TABLES in the database '$DB_NAME'."
        read -p "Are you sure? (y/N): " confirm
        if [[ "$confirm" == "y" || "$confirm" == "Y" ]]; then
            prepare_links
            $MIGRATE -path "$EXE_DIR" -database "$DB_URL" drop
            echo "✅ Database cleaned."
        else
            echo "❌ Operation cancelled."
        fi
        ;;
    *)
        echo "Usage: $0 {up|down|goto V|version|force V}"
        exit 1
        ;;
esac
