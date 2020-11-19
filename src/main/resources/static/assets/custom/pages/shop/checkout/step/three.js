$('#finishSaleForm').on('submit', (event) => {
    event.preventDefault();

    const inputs = $('.sale');
    const sale = {
        customer: {},
        adresses: [],
        items: [],
        usedCreditCards: []
    };
    let i = 0;
    let length = inputs.length;

    let newAddress = true;
    let newItem = true;
    let newCreditCard = true;

    let modelAddress = {
        address: {
            id: null
        }
    }
    let address = {
        ...modelAddress
    };

    let modelItem = {
        product: {
            id: null
        },
        quantity: null,
        subtotal: null
    }
    let item = {
        ...modelItem
    }

    let modelUsedCreditCard = {
        creditCard: {
            id: null
        },
        value: null
    }
    let usedCreditCard = {
        ...modelUsedCreditCard
    }

    for (i = 0; i < length; i++){
        const fieldNames = inputs[i].name.match(/\[(\w+)\]\[(\d+)\]\[(\w+)\]/);
        const currentValue = Number(inputs[i].value);

        if (fieldNames) {
            const parentName = fieldNames[1];
            const attributeName = fieldNames[3];

            switch (parentName) {
                case "customer":
                    const customerId = currentValue;
                    sale.customer = {
                        id: customerId
                    };
                    break;
                case "adresses":
                    if (newAddress) {
                        address = {
                            ...modelAddress
                        };
                        newAddress = false;
                    }
                    if (attributeName === "id") {
                        address.address.id = currentValue;
                    }
                    if (address.id !== null) {
                        sale.adresses.push({...address});
                        newAddress = true;
                    }
                    break;
                case "items":
                    if (newItem) {
                        item = {
                            ...modelItem
                        };
                        newItem = false;
                    }
                    if (attributeName === "id") {
                        item.product.id = currentValue;
                    }
                    if (attributeName === "quantity") {
                        item.quantity = currentValue;
                    }
                    if (attributeName === "subtotal") {
                        item.subtotal = currentValue;
                    }
                    if (item.id !== null && item.quantity !== null && item.subtotal !== null) {
                        sale.items.push({...item});
                        newItem = true;
                    }
                    break;
                case "usedCreditCards":
                    if (newCreditCard) {
                        usedCreditCard = {
                            ...modelUsedCreditCard
                        };
                        newCreditCard = false;
                    }
                    if (attributeName === "id") {
                        usedCreditCard.creditCard.id = currentValue;
                    }
                    if (attributeName === "value") {
                        usedCreditCard.value = currentValue;
                    }
                    if (usedCreditCard.id !== null && usedCreditCard.value !== null) {
                        sale.usedCreditCards.push({...usedCreditCard});
                        newCreditCard = true;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    const saleJSON = JSON.stringify(sale);
    console.log(saleJSON);

    $.ajax({
        method: 'POST',
        url: '/shop/checkout/finish',
        dataType: 'json',
        contentType: 'application/json',
        data: saleJSON,
        success: (response) => {
            console.log(response);
            //window.location.href = '/shop/checkout/finish';
        },
        error: (error) => {
            console.log(error);
        }
    });
});
