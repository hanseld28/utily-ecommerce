ALTER TABLE sales
    DROP CONSTRAINT sls_fgh_fk;

ALTER TABLE sales
    DROP COLUMN sls_fgh_id;

ALTER TABLE freights
    ADD COLUMN fgh_sls_id bigint NOT NULL;

ALTER TABLE freights
    ADD CONSTRAINT fgh_sls_fk FOREIGN KEY (fgh_sls_id) REFERENCES sales(id);


