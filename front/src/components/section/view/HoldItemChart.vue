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
            data: [],
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
  props: ["holdingList"],
  mounted() {
    for (const holding of this.holdingList) {
      this.chartOptionsPie.series[0].data.push({
        name: holding.itemName,
        y: holding.buyTotalAmount,
      });
    }
  },
};
</script>
