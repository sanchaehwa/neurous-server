ALTER TABLE users
    ADD COLUMN pending_module_level_up TINYINT(1) NOT NULL DEFAULT 0;
