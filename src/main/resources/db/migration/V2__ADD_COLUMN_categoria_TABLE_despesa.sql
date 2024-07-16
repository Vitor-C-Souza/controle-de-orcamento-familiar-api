ALTER TABLE `orcamento-familiar-db`.`despesas`
ADD COLUMN `categoria` VARCHAR(50) NOT NULL AFTER `data`;
