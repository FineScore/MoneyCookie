<template>
  <div class="w-3/4 mx-auto my-0">
    <div class="flex justify-between mt-14 mx-10">
      <div colspan="3" class="text-4xl font-bold underline">
        나의 포트폴리오
      </div>
      <div class="flex items-center">
        <h3 class="font-bold">총 보유자산</h3>
        <p class="text-end w-32 ml-5">&#92;1,000,000</p>
      </div>
    </div>
    <div class="flex justify-end mb-10 mx-10">
      <div class="flex items-center">
        <h3 class="font-bold">총 평가수익률</h3>
        <p class="ml-5 w-10 text-end">+20%</p>
      </div>
      <div class="flex items-center">
        <h3 class="font-bold ml-8">총 평가손익</h3>
        <p class="ml-5 w-32 text-end">+&#92;1,000,000</p>
      </div>
    </div>
    <div class="flex justify-around mb-10">
      <div>
        <Highcharts :constructorType="'chart'" :options="chartOptionsPie" />
      </div>
      <div>
        <Highcharts :constructorType="'chart'" :options="chartOptionsColumn" />
      </div>
    </div>
    <div>
      <div class="space-y-2">
        <label for="hold-stocks" class="font-bold">나의 보유 종목</label>

        <div class="mt-7 overflow-x-auto bg-white rounded-lg">
          <table class="w-full whitespace-nowrap">
            <tbody>
              <tr class="h-16 border border-gray-200 bg-gray-200 rounded-lg">
                <th>
                  <div class="flex items-center pl-5">
                    <p class="text-base font-medium leading-none mr-2">
                      종목코드
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      종목명
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      보유수량
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      매수평균가
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      매수금액
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      평가금액
                    </p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center">
                    <p class="text-base font-medium leading-none mr-2">
                      평가손익
                    </p>
                  </div>
                </th>
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
