import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "../components/login/LoginPage.vue";
import SignUpPage from "@/components/signup/SignUpPage.vue";
import BasicPage from "@/components/basic/BasicPage.vue";
import SelectPage from "@/components/select_portfolio/SelectPage.vue";
import ViewPortfolioPage from "@/components/view_portfolio/ViewPortfolioPage";
import AddPage from "@/components/add_portfolio/AddPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: LoginPage,
    },
    {
      path: "/signup",
      component: SignUpPage,
    },
    {
      path: "/portfolio",
      component: BasicPage,
      children: [
        {
          path: "",
          component: SelectPage,
        },
        {
          path: ":id",
          component: ViewPortfolioPage,
        },
        {
          path: "add",
          component: AddPage,
        },
      ],
    },
  ],
});

export default router;
