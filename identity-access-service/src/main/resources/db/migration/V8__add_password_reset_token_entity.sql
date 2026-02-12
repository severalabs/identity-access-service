CREATE TABLE password_reset_token (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    hashed_token VARCHAR(255) NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT uk_hashed_token UNIQUE (hashed_token),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
     REFERENCES users(id) ON DELETE CASCADE
);
