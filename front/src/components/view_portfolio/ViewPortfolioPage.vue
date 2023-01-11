<template>
  <router-link to="/portfolio">
    <div
      class="absolute flex items-center cursor-pointer m-10 opacity-40 hover:opacity-100 transition-all ease-in"
    >
      <img
        src="@/assets/images/right-arrow.png"
        alt="목록으로"
        class="w-14 h-14 rotate-180"
      />
      <p class="text-2xl font-bold text-yellow-600">목록으로</p>
    </div>
  </router-link>
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
        <div class="flex items-center justify-between mx-4 mb-3">
          <label for="hold-stocks" class="font-bold">나의 보유 종목</label>
          <router-link to="edit/1">
            <button
              type="button"
              class="w-28 flex justify-center bg-yellow-600 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
            >
              수정
            </button>
          </router-link>
        </div>

        <div class="mt-7 overflow-x-auto bg-white rounded-lg">
          <table class="w-full whitespace-nowrap">
            <tbody>
              <tr class="h-16 border border-gray-200 bg-gray-200 rounded-lg">
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">종목명</p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">보유수량</p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">매수평균가</p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">매수금액</p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">수익률</p>
                  </div>
                </th>
                <th class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base font-bold leading-none">평가손익</p>
                  </div>
                </th>
              </tr>
              <tr class="h-16 border border-gray-200 rounded-lg">
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">삼성전자</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">10</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">매수평균가</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">매수금액</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">수익률</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">
                      {{ currentAmountFormatted }}
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
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  components: {
    Highcharts: Chart,
  },
  computed: {
    currentAmountFormatted() {
      return Intl.NumberFormat().format(this.currentAmount);
    },
  },
  created() {
    const serverUrl = "http://localhost:8080/ws";
    const socket = new SockJS(serverUrl);
    const stompClient = Stomp.over(socket);
    stompClient.connect(
      {},
      (frame) => {
        console.log("Connected: " + frame);
        stompClient.send(
          "/pub/now",
          JSON.stringify({ ticker: "005930", name: "삼성전자", market: "KS" })
        );
        stompClient.subscribe("/sub/now", (event) => {
          let messages = JSON.parse(event.body);
          this.currentAmount = messages["price"];
        });
      },
      (error) => {
        console.log("Connection Error : " + error.headers.messages);
      }
    );
  },
  data() {
    return {
      currentAmount: 0,
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
