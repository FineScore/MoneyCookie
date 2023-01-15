<template>
  <Highcharts :constructorType="'chart'" :options="chartOptionsPie" />
</template>

<script>
import { Chart } from "highcharts-vue";

export default {
  components: {
    Highcharts: Chart,
  },
  data() {
    return {
      chartOptionsPie: {
        title: {
          text: "",
        },
        chart: {
          type: "pie",
        },
        tooltip: {
          pointFormat: "{point.percentage:.1f}%",
        },
        credits: {
          enabled: false,
        },
        plotOptions: {
          pie: {
            dataLabels: {
              enabled: false,
            },
            showInLegend: true,
          },
        },
        series: [
          {
            data: [
              {
                name: "삼성전자",
                y: 300,
              },
              {
                name: "삼성SDS",
                y: 100,
              },
            ],
          },
        ],
        legend: {
          align: "left",
          verticalAlign: "top",
          layout: "vertical",
        },
      },
    };
  },
};
</script>
