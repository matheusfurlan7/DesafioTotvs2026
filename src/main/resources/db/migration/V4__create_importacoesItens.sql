CREATE TABLE importacao_itens
(
    id            UUID NOT NULL,
    importacao_id UUID NOT NULL,
    linha         TEXT NOT NULL,
    erro          TEXT NOT NULL,
    data_erro     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_importacao_itens PRIMARY KEY (id)
);