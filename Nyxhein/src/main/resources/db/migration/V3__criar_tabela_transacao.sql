CREATE TABLE transacao (
                           id BIGSERIAL PRIMARY KEY,
                           descricao VARCHAR(200) NOT NULL,
                           valor NUMERIC(12, 2) NOT NULL,
                           data_transacao DATE NOT NULL,
                           conta_id BIGINT NOT NULL,
                           categoria_id BIGINT NOT NULL,
                           criado_em TIMESTAMP NOT NULL DEFAULT now(),

                           CONSTRAINT fk_transacao_conta FOREIGN KEY (conta_id) REFERENCES conta(id),
                           CONSTRAINT fk_transacao_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);