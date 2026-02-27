CREATE TABLE contas (
    id UUID PRIMARY KEY,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor NUMERIC(15,2) NOT NULL,
    descricao VARCHAR(255),
    situacao VARCHAR(20) NOT NULL,
    fornecedor_id UUID NOT NULL,
    CONSTRAINT fk_fornecedor
       FOREIGN KEY (fornecedor_id)
           REFERENCES fornecedores(id)
);