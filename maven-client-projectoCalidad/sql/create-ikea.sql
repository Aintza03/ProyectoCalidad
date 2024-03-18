DROP SCHEMA IF EXISTS IKEADB;
DROP USER IF EXISTS 'ikea'@'localhost';

CREATE SCHEMA IKEADB;
CREATE USER IF NOT EXISTS 'ikea'@'localhost' IDENTIFIED BY 'ikea';

GRANT ALL ON IKEADB.* TO 'ikea'@'localhost';
