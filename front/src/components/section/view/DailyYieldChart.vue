<template>
  <Highcharts :constructorType="'chart'" :options="chartOptionsLine" />
</template>

<script>
import { Chart } from "highcharts-vue";
import moment from "moment";

export default {
  props: ["periodicRates"],
  components: {
    Highcharts: Chart,
  },
  data() {
    return {
      chartOptionsLine: {
        title: {
          text: "<b>일간 수익률</b>",
        },

        credits: {
          enabled: false,
        },
        xAxis: {
          categories: [],
        },
        yAxis: {
          title: {
            text: "수익률(%)",
          },
          labels: {
            format: "{value}%",
          },
        },
        legend: {
          enabled: false,
        },
        tooltip: {
          headerFormat: "{point.x} <br>",
          pointFormat: "{point.y}%",
        },

        series: [
          {
            data: [],
          },
        ],
      },
    };
  },
  mounted() {
    for (const rate of this.periodicRates) {
      this.chartOptionsLine.xAxis.categories.push(
        moment(rate.date).format("YYYYMMDD")
      );
      this.chartOptionsLine.series[0].data.push(
        Math.round(rate.totalEvaluationRate * 100) / 100
      );
    }
  },
};
</script>
