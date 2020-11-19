ALTER TABLE products
    RENAME COLUMN
        prt_short_description TO prt_operation;

ALTER TABLE products
    ALTER COLUMN
        prt_operation type varchar(15000);

ALTER TABLE products
    RENAME COLUMN
        prt_long_description TO prt_characteristics;

ALTER TABLE products
    ALTER COLUMN
        prt_characteristics type varchar(5000);



