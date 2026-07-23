CREATE TABLE conta (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       tipo VARCHAR(20) NOT NULL, -- CORRENTE, POUPANCA, CARTEIRA, INVESTIMENTO
                       saldo_inicial NUMERIC(12, 2) NOT NULL DEFAULT 0,
                       criado_em TIMESTAMP NOT NULL DEFAULT now()
);