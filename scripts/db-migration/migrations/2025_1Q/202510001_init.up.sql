-- 検索履歴テーブル
CREATE TABLE search_history (
    id BIGSERIAL PRIMARY KEY,
    postal_code VARCHAR(7) NOT NULL,
    prefecture VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    town VARCHAR(50) NOT NULL,
    search_at TIMESTAMP NOT NULL,

    -- 検索などのためインデックスを貼る想定
    CONSTRAINT idx_search_history_postal_code CHECK (LENGTH(postal_code) = 7)
);

COMMENT ON TABLE search_history IS '郵便番号検索履歴';
COMMENT ON COLUMN search_history.postal_code IS '郵便番号(7桁)';
COMMENT ON COLUMN search_history.search_at IS '検索日時';
