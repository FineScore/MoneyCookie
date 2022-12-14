<template>
  <div>
    <h1 class="text-4xl font-bold underline">나의 포트폴리오</h1>
    <div>
      <h3>총 보유자산</h3>
      <p>&#92;1,000,000</p>
    </div>
    <div>
      <h3>총 평가손익</h3>
      <p>+&#92;1,000,000</p>
    </div>
    <div>
      <h3>총 평가수익률</h3>
      <p>+20%</p>
    </div>
    <div>
      <h3>월별 예상 배당금</h3>
      <Highcharts :constructorType="'chart'" :options="chartOptionsColumn" />
    </div>
    <div>
      <Highcharts :constructorType="'chart'" :options="chartOptionsPie" />
    </div>
    <div>
      <div class="space-y-2">
        <label for="hold-stocks">나의 보유 종목</label>

        <div class="mt-7 overflow-x-auto bg-white rounded-lg">
          <table class="w-full whitespace-nowrap">
            <tbody>
              <tr class="h-16 border border-gray-100 rounded-lg">
                <td>
                  <div class="flex items-center pl-5">
                    <p class="text-base font-medium leading-none mr-2">
                      종목코드
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      종목명
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      보유수량
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      매수평균가
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      매수금액
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      평가금액
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      평가손익
                    </p>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
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
          text: "",
        },
        chart: {
          type: "column",
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
          align: "left",
          verticalAlign: "top",
          layout: "vertical",
        },
      },
    };
  },
};
</script>
