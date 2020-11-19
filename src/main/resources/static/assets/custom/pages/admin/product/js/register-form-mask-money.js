const args = {
    allowNegative: false,
    negativeSignAfter: false,
    prefix: 'R$ ',
    suffix: '',
    fixed: true,
    fractionDigits: 2,
    decimalSeparator: ',',
    thousandsSeparator: '.',
    cursor: 'move'
};

const priceInput = SimpleMaskMoney.setMask('#inputPrice', args);

$('#registerProductForm').on('submit', (event) => {
    event.preventDefault();

    const form = event.target
    const priceNumber = priceInput.formatToNumber();

    if (priceInput <= 0) {
        $('#inputPrice').focus();
    } else {
        form[3].value = priceNumber;
        form.submit();
    }
});