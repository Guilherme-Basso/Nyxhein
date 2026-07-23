CREATE TABLE categoria (
                           id BIGSERIAL PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           tipo VARCHAR(20) NOT NULL, -- RECEITA ou DESPESA
                           criado_em TIMESTAMP NOT NULL DEFAULT now()
);