<template>
  <Highcharts :constructorType="'chart'" :options="chartOptionsColumn" />
</template>

<script>
import { Chart } from "highcharts-vue";

export default {
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
            data: [
              {
                name: "1월",
                y: 1,
              },
              {
                name: "2월",
                y: 2,
              },
              {
                name: "3월",
                y: 4,
              },
              {
                name: "4월",
                y: 2,
              },
              {
                name: "5월",
                y: 6,
              },
              {
                name: "6월",
                y: 6,
              },
              {
                name: "7월",
                y: 3,
              },
              {
                name: "8월",
                y: 3,
              },
              {
                name: "9월",
                y: 3,
              },
              {
                name: "10월",
                y: 3,
              },
              {
                name: "11월",
                y: 3,
              },
              {
                name: "12월",
                y: 4,
              },
            ],
          },
        ],
        legend: {
          enabled: false,
        },
      },
    };
  },
};
</script>
