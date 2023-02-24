<template>
  <router-link to="/section">
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
        {{ title }}
      </div>
      <div class="flex items-center">
        <h3 class="font-bold">총 보유자산</h3>
        <p class="text-end w-32 ml-5">{{ totalAsset }}</p>
      </div>
    </div>
    <div class="flex justify-end mb-10 mx-10">
      <div class="flex items-center">
        <h3 class="font-bold">총 평가수익률</h3>
        <p class="ml-5 w-10 text-end">{{ totalEvaluationRate }}%</p>
      </div>
      <div class="flex items-center">
        <h3 class="font-bold ml-8">총 평가손익</h3>
        <p class="ml-5 w-32 text-end">{{ totalEvaluationAmount }}</p>
      </div>
    </div>
    <div class="flex justify-around mb-10">
      <div class="w-1/3">
        <HoldItemChart :holdingList="holdingList" />
      </div>
      <div class="w-1/3">
        <DailyYieldChart />
      </div>
      <div class="w-1/3">
        <ExpectedDividendChart />
      </div>
    </div>
    <div>
      <div class="space-y-2">
        <div class="flex items-center justify-between mx-4 mb-3">
          <label for="hold-stocks" class="font-bold">나의 보유 종목</label>
          <router-link :to="{ name: 'edit' }">
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
              <tr
                class="h-16 border border-gray-200 rounded-lg"
                v-for="holding in holdingList"
                :key="holding.id"
              >
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">{{ holding.itemName }}</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">{{ holding.quantity }}</p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">
                      {{ currencyFormat(holding.buyAvgPrice) }}
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">
                      {{ currencyFormat(holding.buyTotalAmount) }}
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">
                      {{ round(holding.evaluation.evaluationRate) }}%
                    </p>
                  </div>
                </td>
                <td class="pl-4">
                  <div class="flex items-center justify-center">
                    <p class="text-base leading-none">
                      {{ currencyFormat(holding.evaluation.evaluationAmount) }}
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
import HoldItemChart from "./HoldItemChart.vue";
import ExpectedDividendChart from "./ExpectedDividendChart.vue";
import DailyYieldChart from "./DailyYieldChart.vue";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      stompClient: null,
      section: this.$store.getters.getSection,
    };
  },
  components: {
    HoldItemChart,
    ExpectedDividendChart,
    DailyYieldChart,
  },
  computed: {
    title() {
      return this.section.title;
    },
    totalAsset() {
      return this.currencyFormat(this.section.totalRating.totalAsset);
    },
    totalEvaluationRate() {
      return this.round(this.section.totalRating.totalEvaluationRate);
    },
    totalEvaluationAmount() {
      return this.currencyFormat(
        this.section.totalRating.totalEvaluationAmount
      );
    },
    holdingList() {
      return this.section.holdingList;
    },
  },
  mounted() {
    const serverUrl = "http://localhost:8080/ws";
    const socket = new SockJS(serverUrl);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect(
      {},
      (frame) => {
        console.log("Connected: " + frame);
        if (this.$store.getters.getStatus) {
          this.stompClient.send(
            "/pub/now",
            JSON.stringify(this.$store.getters.getSection)
          );
          this.stompClient.subscribe("/sub/now", (event) => {
            let messages = JSON.parse(event.body);
            console.log(messages);
            this.$store.commit("setSection", messages);
            this.section = this.$store.getters.getSection;
          });
        }
      },
      (error) => {
        console.log("Connection Error : " + error.headers.messages);
      }
    );
  },
  beforeUnmount() {
    this.stompClient.send("/pub/close", "close");
  },
  methods: {
    currencyFormat(money) {
      return Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(money);
    },
    round(rate) {
      return Math.round(rate * 100) / 100;
    },
  },
};
</script>
