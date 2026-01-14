-- ユーザテーブル
CREATE TABLE users (
    id UUID PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    postal_code VARCHAR(7) NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    address_line3 VARCHAR(255)
);

-- コメント定義
COMMENT ON TABLE users IS 'システムを利用するユーザの情報を管理するテーブル';
COMMENT ON COLUMN users.id IS 'プライマリーキー (UUIDv7)';
COMMENT ON COLUMN users.account_name IS 'アカウント名（システム内での一意識別名）';
COMMENT ON COLUMN users.email IS 'メールアドレス';
COMMENT ON COLUMN users.password_hash IS 'ハッシュ化されたパスワード';
COMMENT ON COLUMN users.first_name IS '名';
COMMENT ON COLUMN users.last_name IS '姓';
COMMENT ON COLUMN users.postal_code IS '郵便番号（ハイフンなし7桁）';
COMMENT ON COLUMN users.address_line1 IS '住所1（都道府県・市区町村）';
COMMENT ON COLUMN users.address_line2 IS '住所2（町域・番地）';
COMMENT ON COLUMN users.address_line3 IS '住所3（建物名・部屋番号等）';