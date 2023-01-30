CREATE TABLE bank_account
(
    id                 varchar(36) NOT NULL PRIMARY KEY,
    owner              varchar(255) NOT NULL UNIQUE,
    balance            numeric
);
