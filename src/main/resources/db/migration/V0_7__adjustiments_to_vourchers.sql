ALTER TABLE discount_vouchers
    RENAME TO vouchers;

ALTER TABLE vouchers
    RENAME COLUMN dvc_name TO vch_name;
ALTER TABLE vouchers
    RENAME COLUMN dvc_discount_percent TO vch_multiplication_factor;

ALTER TABLE vouchers
    ALTER COLUMN vch_multiplication_factor type double precision,
    ALTER COLUMN vch_multiplication_factor DROP NOT NULL;

ALTER TABLE vouchers
    ADD COLUMN vch_type varchar(50) NOT NULL,
    ADD COLUMN vch_value double precision;