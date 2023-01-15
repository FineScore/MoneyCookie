<template>
  <Highcharts :constructorType="'chart'" :options="chartOptionsLine" />
</template>

<script>
import { Chart } from "highcharts-vue";

export default {
  components: {
    Highcharts: Chart,
  },
  data() {
    return {
      chartOptionsLine: {
        title: {
          text: "<b>일간 누적 수익률</b>",
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
            name: "Installation & Developers",
            data: [
              43934, 48656, 65165, 81827, 112143, 142383, 171533, 165174,
              155157, 161454, 154610,
            ],
          },
        ],
      },
    };
  },
};
</script>
