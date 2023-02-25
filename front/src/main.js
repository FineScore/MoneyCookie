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

axios.interceptors.response.use((config) => {
  store.commit("setIsLoading", false);

  return config;
});

app.use(router);
app.use(HighchartsVue);
app.use(store);
app.mount("#app");
