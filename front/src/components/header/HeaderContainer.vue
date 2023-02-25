<template>
  <header
    class="sticky h-20 top-0 z-40 w-full flex items-center border-b bg-yellow-400 px-14"
  >
    <nav class="max-w-8xl mx-auto w-full flex items-center justify-between">
      <LogoButton />
      <div class="ml-32">
        <ul class="flex items-center space-x-8">
          <li class="items-center hidden xl:flex">
            <DateTodayHeader />
          </li>
          <li class="flex items-center">
            <StockMarketStatusHeader />
          </li>
          <li class="flex items-center">
            <CurrentTimeHeader />
          </li>
          <li class="flex items-center">
            <UserSettingsButton />
          </li>
          <li>
            <LogoutButton />
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
import DateTodayHeader from "./DateTodayHeader.vue";
import CurrentTimeHeader from "./CurrentTimeHeader.vue";
import StockMarketStatusHeader from "./StockMarketStatusHeader.vue";
import LogoutButton from "./LogoutButton.vue";
import UserSettingsButton from "./UserSettingsButton.vue";
import LogoButton from "./LogoButton.vue";

export default {
  components: {
    DateTodayHeader,
    CurrentTimeHeader,
    StockMarketStatusHeader,
    LogoutButton,
    UserSettingsButton,
    LogoButton,
  },
};
</script>
