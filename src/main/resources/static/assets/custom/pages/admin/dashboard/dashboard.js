const lastThirtyDays = {
    min: moment().subtract(29, 'days').format('YYYY-MM-DD'),
    max: moment().format('YYYY-MM-DD')
};

const handlerIntervalDateFilter = async () => {
    const startDate = $('#startDate').val();
    const endDate = $('#endDate').val();

    const min = new Date(startDate);
    const max = new Date(endDate);

    if (max > min) {
        window.location.href = `http://localhost:8080/admin/dashboard?startDate=${startDate}&endDate=${endDate}`;
    } else {
        alert('O intervalo de datas deve ser válido.');
    }
};

$('#intervalFilter').on('click', handlerIntervalDateFilter);

moment.locale('pt-br');

const COLORS = [
    '#4dc9f6',
    '#f67019',
    '#f53794',
    '#537bc4',
    '#acc236',
    '#166a8f',
    '#00a950',
    '#58595b',
    '#8549ba'
];

const enumerateDaysBetweenDates = function (startDate, endDate) {
    var now = startDate.clone(), dates = [];

    while (now.isSameOrBefore(endDate)) {
        dates.push(now.format('DD/MM/YYYY'));
        now.add(1, 'days');
    }
    return dates;
};

async function getSalesProductByCategoryBetween(startDate, endDate) {
    let chartData = {};

    await $.ajax(`/dashboard/sales/products/categories/interval?startDate=${startDate}&endDate=${endDate}`)
        .then(data => {
            const categories = Object.keys(data);
            const salesData = Object.values(data);

            chartData.labels = enumerateDaysBetweenDates(moment(startDate), moment(endDate));

            const productSalesByDate = salesData.map(series => series.map(serie => ({
                x: serie.date,
                y: serie.productAmount
            })));

            const salesChartSeries = [];

            for (let i = 0; i < categories.length; i++) {
                if (productSalesByDate[i].length === 1) {
                    productSalesByDate[i].unshift({
                        x: moment(productSalesByDate[i][0].x).subtract(1, 'days').format('YYYY-MM-DD'),
                        y: productSalesByDate[i][0].y
                    })
                }
                salesChartSeries.push({
                    label: categories[i],
                    data: productSalesByDate[i],
                    backgroundColor: COLORS[i],
                    borderColor: COLORS[i],
                    fill: false,
                })
            }

            salesChartSeries.unshift({
                name: '(Período)',
                data: [{x: startDate, y: 0}, {x: endDate, y: 0}]
            })

            chartData.datasets = salesChartSeries
                .map((({label, data, backgroundColor, borderColor, fill}) => ({
                    label,
                    data,
                    backgroundColor,
                    borderColor,
                    fill
                })));
        });
    return chartData;
}

$(document).ready(async () => {
    let startDate;
    let endDate;

    let queryString = String(window.location.search);

    if (queryString.trim().length) {
        let params = queryString
            .replace('?', '')
            .split('&');

        startDate = params[0].split('=')[1];
        endDate = params[1].split('=')[1];
    } else {
        startDate = lastThirtyDays.min;
        endDate = lastThirtyDays.max;
    }

    const chartData = await getSalesProductByCategoryBetween(startDate, endDate);
    const ctx = document.getElementById('sales-category-chart').getContext("2d");

    new Chart(ctx, {
        type: 'line',
        data: chartData,
        options: {
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Período'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Vendas de utensílios'
                    }
                }]
            },
            animateScale: true,
        }
    });

    $('#startDate').val(startDate);
    $('#endDate').val(endDate);
})

