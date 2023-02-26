import { createApp } from "vue";
import store from "@/store/index";
import axios from "axios";
import App from "./App.vue";
import router from "@/router/index";
import HighchartsVue from "highcharts-vue";

import "./style.css";

const app = createApp(App);

axios.interceptors.request.use((config) => {
  store.commit("setIsLoading", true);

  return config;
});

axios.interceptors.response.use(
  (response) => {
    store.commit("setIsLoading", false);

    return response;
  },
  (error) => {
    store.commit("setIsLoading", false);

    return Promise.reject(error);
  }
);

app.use(router);
app.use(HighchartsVue);
app.use(store);
app.mount("#app");
