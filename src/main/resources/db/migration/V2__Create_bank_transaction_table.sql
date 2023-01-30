CREATE TABLE bank_transaction
(
    id                 BIGSERIAL    NOT NULL PRIMARY KEY,
    owner              varchar(36)  NOT NULL,
    amount             numeric,
    date               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status             varchar(255),
    type               varchar(255)
);
