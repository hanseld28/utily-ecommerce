CREATE TABLE freights (
	id BIGSERIAL NOT NULL,
	fgh_table varchar(30) NOT NULL,
	fgh_value double precision NOT NULL,
	fgh_estimate_in_days int NOT NULL
);

ALTER TABLE freights
    ADD CONSTRAINT fgh_pk PRIMARY KEY (id);

ALTER TABLE sales
    ADD COLUMN sls_fgh_id bigint;

ALTER TABLE sales
    ADD CONSTRAINT sls_fgh_fk FOREIGN KEY (sls_fgh_id) REFERENCES freights(id);

ALTER TABLE trades
    RENAME COLUMN trd_tracking_number TO trd_number;