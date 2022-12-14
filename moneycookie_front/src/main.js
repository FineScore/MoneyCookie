import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router/index";
import HighchartsVue from "highcharts-vue";

import "./style.css";

const app = createApp(App);

app.use(router);
app.use(HighchartsVue);
app.mount("#app");
