DROP TABLE products_categories
    CASCADE;

ALTER TABLE products
    ADD COLUMN prt_ctg_id bigint;

ALTER TABLE ONLY public.products
    ADD CONSTRAINT prt_ctg_fk FOREIGN KEY (prt_ctg_id) REFERENCES public.categories(id);

ALTER TABLE products
    ALTER COLUMN prt_height TYPE VARCHAR(20),
    ALTER COLUMN prt_width TYPE VARCHAR(20),
    ALTER COLUMN prt_weight TYPE VARCHAR(20),
    ALTER COLUMN prt_depth TYPE VARCHAR(20);