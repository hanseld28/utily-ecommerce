ALTER TABLE customers_vouchers
    ADD COLUMN cvh_registered_at timestamp without time zone NOT NULL;

ALTER TABLE customers_vouchers
    DROP CONSTRAINT cvh_pk;

ALTER TABLE customers_vouchers
    ADD CONSTRAINT cvh_pk PRIMARY KEY (cvh_cst_id, cvh_vch_id, cvh_registered_at);

