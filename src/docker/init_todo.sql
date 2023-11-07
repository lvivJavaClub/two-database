CREATE TABLE IF NOT EXISTS `todo` (
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `text`          VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
