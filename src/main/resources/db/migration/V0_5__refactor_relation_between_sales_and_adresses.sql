ALTER TABLE sales
    DROP COLUMN
        sls_adr_id;

CREATE TABLE IF NOT EXISTS sales_adresses (
	ssa_sls_id bigint,
	ssa_adr_id bigint
);

ALTER TABLE ONLY public.sales_adresses
    ADD CONSTRAINT ssa_pk PRIMARY KEY (ssa_sls_id, ssa_adr_id);

ALTER TABLE ONLY public.sales_adresses
    ADD CONSTRAINT ssa_sls_fk FOREIGN KEY (ssa_sls_id) REFERENCES public.sales(id);

ALTER TABLE ONLY public.sales_adresses
    ADD CONSTRAINT ssa_adr_fk FOREIGN KEY (ssa_adr_id) REFERENCES public.adresses(id);