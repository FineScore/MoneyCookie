import { createRouter, createWebHistory } from "vue-router";
import store from "@/store/index";
import LoginContainer from "../components/login/LoginContainer.vue";
import SignUpContainer from "@/components/signup/SignUpContainer.vue";
import SectionContainer from "@/components/section/SectionContainer.vue";
import SectionSelector from "@/components/section/select/SectionSelector.vue";
import ViewContainer from "@/components/section/view/ViewContainer.vue";
import CreateContainer from "@/components/section/create/CreateContainer.vue";
import EditContainer from "@/components/section/edit/EditContainer.vue";
import UserInfoContainer from "@/components/userinfo/UserInfoContainer.vue";
import NotFound from "@/components/error/NotFound.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: LoginContainer,
    },
    {
      path: "/register",
      component: SignUpContainer,
    },
    {
      path: "/section",
      name: "section",
      component: SectionContainer,
      beforeEnter: (to, from, next) => {
        if (!store.getters.getIsLogin) {
          next("/");
        } else {
          next();
        }
      },
      children: [
        {
          path: "",
          component: SectionSelector,
        },
        {
          path: ":sequence",
          name: "sequence",
          component: ViewContainer,
        },
        { path: ":sequence/edit", name: "edit", component: EditContainer },
        {
          path: "create",
          component: CreateContainer,
        },
        {
          path: "user",
          component: UserInfoContainer,
        },
      ],
    },
    { path: "/:pathMatch(.*)*", name: "NotFound", component: NotFound },
  ],
});

export default router;
