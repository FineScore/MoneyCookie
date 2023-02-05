CREATE TABLE IF NOT EXISTS `member`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY,
    `email`       VARCHAR(45) NOT NULL,
    `password`    VARCHAR(45) NOT NULL,
    `nickname`    VARCHAR(45) NOT NULL,
    `create_date` TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS `section`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY,
    `member_id`   INT         NOT NULL,
    `section_num` INT         NOT NULL,
    `title`       VARCHAR(50) NOT NULL,
    `create_date` TIMESTAMP   NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `items_kr`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `ticker`    VARCHAR(45) NOT NULL,
    `item_name` VARCHAR(45) NOT NULL,
    `market`    VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS `holdings`
(
    `id`         INT AUTO_INCREMENT PRIMARY KEY,
    `section_id` INT       NOT NULL,
    `item_kr_id` INT       NOT NULL,
    `quantity`   INT       NOT NULL,
    `buy_price`  INT       NOT NULL,
    `buy_date`   TIMESTAMP NOT NULL,
    FOREIGN KEY (section_id)
        REFERENCES `section` (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (item_kr_id)
        REFERENCES `items_kr` (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
