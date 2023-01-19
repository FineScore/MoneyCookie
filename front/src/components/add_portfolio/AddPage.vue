<template>
  <form class="w-2/3 mx-auto mt-32 space-y-6">
    <div class="space-y-2">
      <label for="nickname">포트폴리오명</label>
      <input
        type="text"
        name="p-name"
        id="p-name"
        value="나의 포트폴리오"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
      />
    </div>
    <div class="space-y-2">
      <label for="hold-stocks">나의 보유 종목</label>
      <div class="bg-gray-300 py-6 px-6 rounded">
        <div class="">
          <div class="relative sm:flex items-center">
            <input
              type="text"
              name="p-name"
              id="p-name"
              placeholder="종목코드 또는 종목명을 입력하세요."
              autocomplete="off"
              class="w-1/4 peer text-sm px-4 py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
              :value="itemName"
              @input="setItemName"
              @focus="toggleItemList"
            />
            <div
              v-show="isVisible"
              class="absolute top-11 rounded drop-shadow bg-white overflow-hidden w-1/4 mt-1 border border-gray-200"
            >
              <div class="cursor-pointer group">
                <button
                  type="button"
                  class="w-full px-4 py-2 border-transparent border-l-4 group-hover:border-yellow-400 group-hover:bg-gray-100"
                  value="005930"
                  @click="insertItemBtn"
                >
                  005930
                </button>
              </div>
              <div class="cursor-pointer group">
                <div
                  class="block px-4 py-2 border-transparent border-l-4 group-hover:border-yellow-400 group-hover:bg-gray-100"
                >
                  BBB
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="mt-7 overflow-x-auto bg-white rounded-lg">
          <table class="w-full whitespace-nowrap">
            <tbody>
              <tr
                v-if="items.length === 0"
                class="h-16 border border-gray-100 rounded-lg"
              >
                <td class="font-medium text-center">
                  보유 종목을 추가해주세요.
                </td>
              </tr>
              <div v-else>
                <tr
                  v-for="item in items"
                  :key="item.ticker"
                  class="flex items-center justify-around h-16 border border-gray-100 rounded-lg"
                >
                  <td>
                    <div class="flex items-center pl-5">
                      <p class="text-base font-medium leading-none mr-2">
                        {{ item.ticker }}
                      </p>
                    </div>
                  </td>
                  <td class="pl-4">
                    <div class="flex items-center">
                      <p class="text-base font-medium leading-none mr-2">
                        {{ item.name }}
                      </p>
                    </div>
                  </td>
                  <td class="pl-4">
                    <div class="flex items-center">
                      <input
                        type="number"
                        min="0"
                        name="amount"
                        id="amount"
                        placeholder="보유수량"
                        class="w-32 text-sm px-4 py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                      />
                    </div>
                  </td>
                  <td class="pl-4">
                    <div class="flex items-center">
                      <input
                        type="text"
                        name="avg-buy"
                        id="avg-buy"
                        placeholder="매수평균가"
                        class="w-40 text-sm px-4 py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                      />
                    </div>
                  </td>
                  <td>
                    <div class="flex items-center">
                      <Datepicker
                        v-model="date"
                        :enable-time-picker="false"
                        auto-apply
                        locale="ko"
                        format="yyyy/MM/dd"
                        placeholder="매수일자"
                        class="w-44"
                      />
                    </div>
                  </td>
                  <td>
                    <button
                      type="button"
                      class="w-20 text-red-500 active:text-white border border-red-500 active:bg-red-500 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
                    >
                      삭제
                    </button>
                  </td>
                </tr>
              </div>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="h-px bg-gray-200"></div>
    <div>
      <router-link to="/portfolio/1">
        <button
          type="submit"
          class="w-full flex justify-center bg-yellow-600 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
        >
          포트폴리오 추가
        </button>
      </router-link>
    </div>
  </form>
</template>

<script>
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";

export default {
  components: { Datepicker },
  data() {
    return {
      date: null,
      itemName: "",
      items: [{ ticker: "005930", name: "삼성전자", market: "KOSPI" }],
      isVisible: false,
    };
  },
  methods: {
    addItems() {
      return this.items.push();
    },
    setItemName(event) {
      this.itemName = event.target.value;
    },
    insertItemBtn(event) {
      // console.log(event.target.value);
      this.itemName = event.target.value;
      this.isVisible = !this.isVisible;
    },
    toggleItemList() {
      this.isVisible = !this.isVisible;
    },
  },
};
</script>
