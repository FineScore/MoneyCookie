CREATE TABLE IF NOT EXISTS `moneycookie`.`users` (
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`sections` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `title` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sections_users1_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_sections_users1`
    FOREIGN KEY (`username`)
    REFERENCES `moneycookie`.`users` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`total_ratings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT NOT NULL,
  `total_asset` BIGINT NOT NULL,
  `total_evaluation_rate` DOUBLE NOT NULL,
  `total_evaluation_amount` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_total_ratings_sections1_idx` (`section_id` ASC) VISIBLE,
  CONSTRAINT `fk_total_ratings_sections1`
    FOREIGN KEY (`section_id`)
    REFERENCES `moneycookie`.`sections` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`items_kr` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ticker` VARCHAR(45) NOT NULL,
  `item_name` VARCHAR(45) NOT NULL,
  `market` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`closed_days` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`holdings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT NOT NULL,
  `item_kr_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `buy_avg_price` INT NOT NULL,
  `buy_total_amount` BIGINT NOT NULL,
  `buy_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_section_buy_section1_idx` (`section_id` ASC) VISIBLE,
  INDEX `fk_section_buy_listed_item_kr1_idx` (`item_kr_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_buy_section1`
    FOREIGN KEY (`section_id`)
    REFERENCES `moneycookie`.`sections` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_section_buy_listed_item_kr1`
    FOREIGN KEY (`item_kr_id`)
    REFERENCES `moneycookie`.`items_kr` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `moneycookie`.`evaluations` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `holding_id` BIGINT NOT NULL,
  `evaluation_rate` DOUBLE NOT NULL,
  `evaluation_amount` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_evaluations_holdings1_idx` (`holding_id` ASC) VISIBLE,
  CONSTRAINT `fk_evaluations_holdings1`
    FOREIGN KEY (`holding_id`)
    REFERENCES `moneycookie`.`holdings` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;