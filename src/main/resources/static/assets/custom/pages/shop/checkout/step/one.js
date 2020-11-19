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

const valueUsedInput = SimpleMaskMoney.setMask('#valueUsedInput', args);

$('#formCreditCardPayment').on('submit', (event) => {
    event.preventDefault();

    const form = event.target
    const valueUsedNumber = valueUsedInput.formatToNumber();

    if (valueUsedNumber <= 0) {
        $('#valueUsedInput').focus();
    } else {
        form[1].value = valueUsedNumber;
        form.submit();
    }
});