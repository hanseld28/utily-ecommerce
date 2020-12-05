ALTER TABLE vouchers RENAME CONSTRAINT dvc_pk TO vch_pk;

ALTER SEQUENCE dvc_id_seq RENAME TO vch_id_seq;

CREATE TABLE customers_vouchers (
	cvh_cst_id bigint NOT NULL,
	cvh_vch_id bigint NOT NULL,
	cvh_used boolean default FALSE
);

ALTER TABLE ONLY  public.customers_vouchers
    ADD CONSTRAINT cvh_pk PRIMARY KEY (cvh_cst_id, cvh_vch_id);

ALTER TABLE ONLY public.customers_vouchers
    ADD CONSTRAINT cvh_cst_fk FOREIGN KEY (cvh_cst_id) REFERENCES public.customers(id);

ALTER TABLE ONLY public.customers_vouchers
    ADD CONSTRAINT cvh_vch_fk FOREIGN KEY (cvh_vch_id) REFERENCES public.vouchers(id);

