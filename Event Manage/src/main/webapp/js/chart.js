function renderDashboardChart() {
    fetch('http://localhost:8080/eventmanage_war_exploded/admin?action=get-events-chart')
        .then(response => response.json())
        .then(result => {
            const events = result.data;
            const chartData = events.map(event => ({
                x: new Date(event.date),
                y: 1,
                label: event.name
            }));

            const chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "light2",
                title: {
                    text: "Events by Date"
                },
                axisY: {
                    title: "",
                    lineThickness: 0,
                    tickLength: 0,
                    labelFormatter: () => " ",
                },
                axisX: {
                    title: "Date",
                    valueFormatString: "DD MMM YYYY",
                    labelAngle: -45
                },
                data: [{
                    type: "scatter",
                    toolTipContent: "<b>{label}</b><br/>{x}",
                    dataPoints: chartData
                }]
            });

            chart.render();
        })
        .catch(error => {
            console.error("Error fetching data: ", error);
        });
}
