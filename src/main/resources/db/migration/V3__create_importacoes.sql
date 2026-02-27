CREATE TABLE importacoes
(
    id           UUID    NOT NULL,
    total_linhas INTEGER NOT NULL,
    processadas  INTEGER NOT NULL,
    sucesso      INTEGER NOT NULL,
    erro         INTEGER NOT NULL,
    situacao     VARCHAR(255),
    data_inicio  TIMESTAMP WITHOUT TIME ZONE,
    data_fim     TIMESTAMP WITHOUT TIME ZONE,
    versao       BIGINT,
    CONSTRAINT pk_importacoes PRIMARY KEY (id)
);