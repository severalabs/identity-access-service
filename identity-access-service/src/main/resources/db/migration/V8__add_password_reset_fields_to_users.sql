ALTER TABLE User
ADD COLUMN passwordResetTokenHashed VARCHAR(255),
ADD COLUMN passwordResetTokenValidityDuration TIMESTAMP;
