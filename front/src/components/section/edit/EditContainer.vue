<template>
  <form class="w-2/3 mx-auto mt-32 space-y-6">
    <div class="space-y-2">
      <label for="title">포트폴리오명</label>
      <input
        type="text"
        name="title"
        id="title"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="title"
      />
    </div>
    <div class="space-y-2">
      <label for="holdinds">나의 보유 종목</label>
      <div class="bg-gray-300 py-6 px-6 rounded">
        <div class="">
          <div class="relative sm:flex items-center">
            <input
              type="text"
              name="search"
              id="search"
              placeholder="종목코드 또는 종목명을 입력하세요."
              autocomplete="off"
              class="w-1/4 peer text-sm px-4 py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
              :value="itemName"
              @input="searchItem"
            />
            <tr
              v-show="isVisible"
              class="absolute top-11 rounded drop-shadow bg-white overflow-hidden w-1/4 mt-1 border border-gray-200"
            >
              <td
                v-for="item in limitSearchList"
                :key="item.id"
                class="flex flex-col"
              >
                <div class="cursor-pointer group">
                  <button
                    type="button"
                    class="w-full px-4 py-2 border-transparent border-l-4 group-hover:border-yellow-400 group-hover:bg-gray-100"
                    :value="item.id"
                    @click="insertItem(item)"
                  >
                    <div class="flex items-center justify-between">
                      <p>
                        {{ item.ticker }}
                      </p>
                      <p>
                        {{ item.itemName }}
                      </p>
                      <p>
                        {{ item.market }}
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
                        name="quantity"
                        id="quantity"
                        placeholder="보유수량"
                        autocomplete="off"
                        :disabled="items[index].updateStatus === 'DELETE'"
                        class="w-32 text-sm text-center py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                        v-model.number="items[index].quantity"
                        @input="setUpdateMode(index)"
                      />
                    </div>
                  </td>
                  <td>
                    <div class="flex justify-center">
                      <input
                        type="text"
                        name="buy-avg-price"
                        id="buy-avg-price"
                        placeholder="매수평균가"
                        autocomplete="off"
                        :disabled="items[index].updateStatus === 'DELETE'"
                        class="w-40 text-sm text-center py-3 focus:bg-white bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                        v-model="items[index].buyAvgPrice"
                        @input="setUpdateMode(index)"
                      />
                    </div>
                  </td>
                  <td>
                    <div class="flex justify-center">
                      <Datepicker
                        v-model="items[index].buyDate"
                        :enable-time-picker="false"
                        :disabled="items[index].updateStatus === 'DELETE'"
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
                      class="w-20 text-red-500 active:text-white border border-red-500 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
                      :class="{
                        'bg-red-500 text-black':
                          items[index].updateStatus === 'DELETE',
                      }"
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
      <strong
        class="flex justify-center my-4 text-sm text-red-600 font-semibold"
        v-if="!isValidate"
        >미입력된 정보가 있습니다.</strong
      >
      <div></div>
      <button
        type="button"
        @click="update"
        class="w-full flex justify-center bg-yellow-600 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        포트폴리오 수정
      </button>
    </div>
  </form>
</template>

<script>
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import axios from "axios";
import moment from "moment";
import { useVuelidate } from "@vuelidate/core";
import { required, numeric, helpers } from "@vuelidate/validators";

export default {
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  components: { Datepicker },
  data() {
    return {
      title: "",
      itemName: "",
      searchItemList: [],
      items: [],
      isVisible: false,
      deleteMode: false,
      isValidate: true,
    };
  },
  validations() {
    return {
      title: { required },
      items: {
        required,
        $each: helpers.forEach({
          quantity: {
            required,
          },
          buyAvgPrice: {
            required,
            numeric,
          },
          buyDate: {
            required,
          },
        }),
      },
    };
  },
  mounted() {
    this.title = this.$store.getters.getTitle;
    this.items = this.$store.getters.getHoldingList;
  },
  computed: {
    limitSearchList() {
      if (this.searchItemList !== undefined) {
        return this.searchItemList.slice(0, 10);
      }
      return undefined;
    },
  },
  methods: {
    searchItem(event) {
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
          this.searchItemList = response.data.data;
        })
        .catch((error) => {
          console.log(error);
          this.$store.commit("setError", error.response.data);
        });
      this.isVisible = true;
    },
    insertItem(searchItem) {
      const item = {
        itemKrId: searchItem.id,
        ticker: searchItem.ticker,
        itemName: searchItem.itemName,
        updateStatus: "INSERT",
      };
      console.log(item);
      this.items.push(item);
      this.closeSearchList();
      this.itemName = "";
    },
    closeSearchList() {
      this.isVisible = false;
    },
    deleteItem(index) {
      if (this.items[index].updateStatus === "DELETE") {
        this.items[index].updateStatus = null;
        return;
      }

      if ("id" in this.items[index] === false) {
        this.items.splice(index, 1);
      } else {
        this.items[index].updateStatus = "DELETE";
      }
    },
    setUpdateMode(index) {
      if ("id" in this.items[index] === true) {
        this.items[index].updateStatus = "UPDATE";
      }
    },
    async update() {
      const holdingList = this.setHoldingList(this.items);
      const isValidate = await this.v$.$validate();

      if (isValidate) {
        const url = "/api/section";

        const data = {
          id: this.$store.getters.getId,
          title: this.title,
          holdingList: holdingList,
        };
        console.log(data);

        axios
          .put(url, data)
          .then((response) => {
            console.log(response.data);
            this.$router.push("/section");
          })
          .catch((error) => {
            console.log(error.response.data);
            this.$store.commit("setError", error.response.data);
          });
      }
    },
    setHoldingList(items) {
      const holdingList = [];
      for (const item of items) {
        if (item.updateStatus === "INSERT") {
          const holding = {
            sectionId: this.$store.getters.getId,
            itemKrId: item.itemKrId,
            quantity: item.quantity,
            buyAvgPrice: item.buyAvgPrice,
            buyDate: moment(item.buyDate).format("YYYY-MM-DD"),
            updateStatus: item.updateStatus,
          };
          holdingList.push(holding);
        } else if (item.updateStatus === "UPDATE") {
          const holding = {
            id: item.id,
            sectionId: this.$store.getters.getId,
            itemKrId: item.itemKrId,
            quantity: item.quantity,
            buyAvgPrice: item.buyAvgPrice,
            buyDate: moment(item.buyDate).format("YYYY-MM-DD"),
            updateStatus: item.updateStatus,
          };
          holdingList.push(holding);
        } else if (item.updateStatus === "DELETE") {
          const holding = {
            id: item.id,
            sectionId: this.$store.getters.getId,
            updateStatus: item.updateStatus,
          };
          holdingList.push(holding);
        }
      }

      return holdingList;
    },
  },
};
</script>
