import { createStore } from "vuex";
import VuexPersistence from "vuex-persist";

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

const store = createStore({
  state() {
    return {
      sectionList: [],
      index: 0,
      isLoading: false,
      isLogin: false,
      currentTime: "",
      todayDate: "",
      status: true,
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
    updateSection(state, data) {
      const currentSection = state.sectionList[state.index];
      currentSection.totalRating = data.totalRating;
      const currentHoldingList = currentSection.holdingList;
      for (let i = 0; i < currentHoldingList.length; i++) {
        currentHoldingList[i].evaluation = data.holdingList[i].evaluation;
      }
    },
    setIsLoading(state, data) {
      state.isLoading = data;
    },
    setIsLogin(state, data) {
      state.isLogin = data;
    },
    setCurrentTime(state, data) {
      state.currentTime = data;
    },
    setTodayDate(state, data) {
      state.todayDate = data;
    },
    setStatus(state, data) {
      state.status = data;
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
    getHoldingList(_, getters) {
      return getters.getSection.holdingList;
    },
    getIsLogin(state) {
      return state.isLogin;
    },
    getStatus(state) {
      return state.status;
    },
  },
  plugins: [vuexLocal.plugin],
});

export default store;
