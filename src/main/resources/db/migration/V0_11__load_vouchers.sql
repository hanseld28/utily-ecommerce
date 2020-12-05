INSERT INTO
    vouchers(
		id,
		vch_name,
		vch_multiplication_factor,
		vch_type,
		vch_value,
		inactivated
	)
	VALUES
		(nextval('vch_id_seq'), 'Cupom de 5% de desconto', 0.05, 'DISCOUNT', NULL, false),
		(nextval('vch_id_seq'), 'Cupom de 10% de desconto', 0.1, 'DISCOUNT', NULL, false),
		(nextval('vch_id_seq'), 'Cupom de 15% de desconto', 0.15, 'DISCOUNT', NULL, false),
		(nextval('vch_id_seq'), 'Cupom de 25% de desconto', 0.25, 'DISCOUNT', NULL, false),
		(nextval('vch_id_seq'), 'Cupom de 50% de desconto', 0.5, 'DISCOUNT', NULL, false),
		(nextval('vch_id_seq'), 'Cupom de 60% de desconto', 0.6, 'DISCOUNT', NULL, false);