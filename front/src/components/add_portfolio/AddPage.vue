<template>
  <form class="w-2/3 mx-auto mt-32 space-y-6">
    <div class="space-y-2">
      <label for="p-name">포트폴리오명</label>
      <input
        type="text"
        name="p-name"
        id="p-name"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="title"
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
            />
            <tr
              v-show="isVisible"
              class="absolute top-11 rounded drop-shadow bg-white overflow-hidden w-1/4 mt-1 border border-gray-200"
            >
              <td
                v-for="searchItem in filteredItemList"
                :key="searchItem.id"
                class="flex flex-col"
              >
                <div class="cursor-pointer group">
                  <button
                    type="button"
                    class="w-full px-4 py-2 border-transparent border-l-4 group-hover:border-yellow-400 group-hover:bg-gray-100"
                    :value="searchItem.id"
                    @click="insertItemBtn(searchItem)"
                  >
                    <div class="flex items-center justify-between">
                      <p>
                        {{ searchItem.ticker }}
                      </p>
                      <p>
                        {{ searchItem.itemName }}
                      </p>
                      <p>
                        {{ searchItem.market }}
                      </p>
                    </div>
                  </button>
                </div>
              </td>
            </tr>
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
                  v-for="(item, index) in items"
                  :key="item.ticker"
                  class="flex items-center justify-around content-center h-16 border border-gray-100 rounded-lg"
                >
                  <td>
                    <div class="flex justify-center">
                      <p class="text-base font-medium leading-none">
                        {{ item.ticker }}
                      </p>
                    </div>
                  </td>
                  <td class="w-1/5">
                    <div class="flex justify-center">
                      <p class="text-base font-medium leading-none">
                        {{ item.itemName }}
                      </p>
                    </div>
                  </td>
                  <td>
                    <div class="flex justify-center">
                      <input
                        type="number"
                        min="0"
                        name="amount"
                        id="amount"
                        placeholder="보유수량"
                        autocomplete="off"
                        class="w-32 text-sm text-center py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                        v-model.number="items[index].quantity"
                      />
                    </div>
                  </td>
                  <td>
                    <div class="flex justify-center">
                      <input
                        type="text"
                        name="avg-buy"
                        id="avg-buy"
                        placeholder="매수평균가"
                        autocomplete="off"
                        class="w-40 text-sm text-center py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                        v-model="items[index].buyAvgPrice"
                      />
                    </div>
                  </td>
                  <td>
                    <div class="flex justify-center">
                      <Datepicker
                        v-model="items[index].buyDate"
                        :enable-time-picker="false"
                        auto-apply
                        locale="ko"
                        format="yyyy/MM/dd"
                        placeholder="매수일자"
                        class="w-44 text-center"
                      />
                    </div>
                  </td>
                  <td>
                    <button
                      type="button"
                      class="w-20 text-red-500 active:text-white border border-red-500 active:bg-red-500 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
                      @click="deleteItem(index)"
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
      <button
        type="button"
        @click="submitSection"
        class="w-full flex justify-center bg-yellow-600 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        포트폴리오 추가
      </button>
    </div>
    <div>
      <button
        type="button"
        @click="requestSaveItemList"
        class="w-full flex justify-center bg-yellow-600 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        테스트용 아이템 목록 저장
      </button>
    </div>
  </form>
</template>

<script>
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import axios from "axios";
import moment from "moment";

export default {
  components: { Datepicker },
  data() {
    return {
      title: "나의 포트폴리오",
      itemName: "",
      searchItemList: [],
      items: [],
      isVisible: false,
    };
  },
  computed: {
    filteredItemList() {
      return this.searchItemList.slice(0, 10);
    },
  },
  methods: {
    setItemName(event) {
      this.itemName = event.target.value;
      const url = "/api/search";

      axios
        .get(url, {
          params: {
            keyword: this.itemName,
          },
        })
        .then((response) => {
          console.log(response.data);
          this.searchItemList = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
      this.isVisible = true;
    },
    insertItemBtn(itemInfo) {
      // console.log(event.target.value);
      const item = {
        itemKrId: itemInfo.id,
        ticker: itemInfo.ticker,
        itemName: itemInfo.itemName,
      };
      this.items.push(item);
      this.closeItemList();
      this.itemName = "";
    },
    toggleItemList() {
      this.isVisible = !this.isVisible;
    },
    closeItemList() {
      this.isVisible = false;
    },
    deleteItem(index) {
      this.items.splice(index, 1);
    },
    requestSaveItemList() {
      const url = "/test-item";

      axios
        .post(url)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    submitSection() {
      const holdingList = [];
      for (const item of this.items) {
        const holding = {
          itemKrId: item.itemKrId,
          quantity: item.quantity,
          buyAvgPrice: item.buyAvgPrice,
          buyDate: moment(item.buyDate).format("YYYY-MM-DD"),
        };
        holdingList.push(holding);
      }
      const url = "/api/section";

      const data = {
        title: this.title,
        holdingList: holdingList,
      };
      console.log(data);

      axios
        .post(url, data)
        .then((response) => {
          console.log(response.data);
          this.$router.push("/section");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
