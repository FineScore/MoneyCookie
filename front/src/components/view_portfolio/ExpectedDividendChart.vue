<template>
  <Highcharts :constructorType="'chart'" :options="chartOptionsColumn" />
</template>

<script>
import { Chart } from "highcharts-vue";

export default {
  props: ["dividends"],
  components: {
    Highcharts: Chart,
  },
  data() {
    return {
      chartOptionsColumn: {
        title: {
          text: "<b>월별 예상 배당금</b>",
        },
        chart: {
          type: "column",
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: [
            "1월",
            "2월",
            "3월",
            "4월",
            "5월",
            "6월",
            "7월",
            "8월",
            "9월",
            "10월",
            "11월",
            "12월",
          ],
        },
        yAxis: {
          title: {
            text: "원(&#8361;)",
          },
          labels: {
            format: "&#8361; {value}",
          },
        },
        tooltip: {
          headerFormat: "{point.x} 예상 배당금 <br>",
          pointFormat: "&#8361;{point.y}",
        },
        series: [
          {
            data: [],
          },
        ],
        legend: {
          enabled: false,
        },
      },
    };
  },
  mounted() {
    for (const key in this.dividends) {
      this.chartOptionsColumn.series[0].data.push({
        name: key + "월",
        y: this.dividends[key],
      });
    }
  },
};
</script>
