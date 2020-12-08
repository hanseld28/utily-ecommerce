ALTER TABLE trades
    DROP CONSTRAINT trd_prt_fk;

ALTER TABLE trades
    DROP COLUMN
        trd_prt_id;

ALTER TABLE trades
    DROP COLUMN
        trd_quantity;

ALTER TABLE trades
    DROP COLUMN
        inactivated;

CREATE TABLE trades_products (
	trp_trd_id bigint NOT NULL,
	trp_prt_id bigint NOT NULL,
	trp_quantity int NOT NULL,
	trp_reason varchar(500),
    trp_registered_at timestamp without time zone NOT NULL
);

ALTER TABLE trades_products
    ADD CONSTRAINT trp_pk PRIMARY KEY (trp_trd_id, trp_prt_id, trp_registered_at);

ALTER TABLE trades_products
    ADD CONSTRAINT trp_trd_fk FOREIGN KEY (trp_trd_id) REFERENCES trades(id);

ALTER TABLE trades_products
    ADD CONSTRAINT trp_prt_fk FOREIGN KEY (trp_prt_id) REFERENCES products(id);
