CREATE TABLE IF NOT EXISTS `user` (
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `username`      VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
