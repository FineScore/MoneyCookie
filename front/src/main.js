import { createApp } from "vue";
import { createStore } from "vuex";
import App from "./App.vue";
import router from "@/router/index";
import HighchartsVue from "highcharts-vue";
import axios from "axios";

import "./style.css";

const store = createStore({
  state() {
    return {
      sectionList: [],
      index: 0,
      status: true,
      isLoading: false,
    };
  },
  mutations: {
    setSectionList(state, data) {
      state.sectionList = data;
    },
    setIndex(state, sequence) {
      state.index = sequence - 1;
    },
    setSection(state, data) {
      state.sectionList[state.index] = data;
    },
    setStatus(state, data) {
      state.status = data;
    },
    setIsLoading(state, data) {
      state.isLoading = data;
    },
  },
  getters: {
    getSection(state) {
      return state.sectionList[state.index];
    },
    getId(_, getters) {
      return getters.getSection.id;
    },
    getTitle(_, getters) {
      return getters.getSection.title;
    },
    getTotalAsset(_, getters) {
      return getters.getSection.totalRating.totalAsset;
    },
    getTotalEvaluationRate(_, getters) {
      return getters.getSection.totalRating.totalEvaluationRate;
    },
    getTotalEvaluationAmount(_, getters) {
      return getters.getSection.totalRating.totalEvaluationAmount;
    },
    getHoldingList(_, getters) {
      return getters.getSection.holdingList;
    },
    getStatus(state) {
      return state.status;
    },
    getPeriodicRates(_, getters) {
      return getters.getSection.periodicRates;
    },
    getIsLoading(state) {
      return state.isLoading;
    },
  },
});

axios.interceptors.request.use((config) => {
  store.commit("setIsLoading", true);

  return config;
});

axios.interceptors.response.use((config) => {
  store.commit("setIsLoading", false);

  return config;
});

const app = createApp(App);

app.use(router);
app.use(HighchartsVue);
app.use(store);
app.mount("#app");

// app.config.globalProperties.connection = new WebSocket("ws://localhost:8081/");
