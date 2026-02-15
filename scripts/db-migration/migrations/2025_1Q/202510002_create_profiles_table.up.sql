-- Profiles table for user profile management
CREATE TABLE profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    biography VARCHAR(500),
    profile_image_url VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_profiles_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_profiles_user_id ON profiles(user_id);
CREATE INDEX idx_profiles_status ON profiles(status);

-- Comments
COMMENT ON TABLE profiles IS 'User profile information including biography and profile image';
COMMENT ON COLUMN profiles.id IS 'Primary key (UUID)';
COMMENT ON COLUMN profiles.user_id IS 'Reference to users table';
COMMENT ON COLUMN profiles.biography IS 'User biography text (max 500 characters)';
COMMENT ON COLUMN profiles.profile_image_url IS 'Profile image URL';
COMMENT ON COLUMN profiles.status IS 'Profile status (DRAFT, PUBLISHED, ARCHIVED)';
COMMENT ON COLUMN profiles.created_at IS 'Creation timestamp';
COMMENT ON COLUMN profiles.updated_at IS 'Last update timestamp';
