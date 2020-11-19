DROP TABLE sales_discount_vouchers
    CASCADE;

ALTER TABLE sales
    ADD COLUMN sls_vch_id bigint;

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sls_vch_fk FOREIGN KEY (sls_vch_id) REFERENCES public.vouchers(id);