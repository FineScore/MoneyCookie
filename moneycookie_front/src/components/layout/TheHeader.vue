<template>
  <header
    class="sticky h-20 top-0 z-40 w-full flex items-center border-b bg-yellow-400"
  >
    <nav class="max-w-8xl mx-auto w-full flex items-center justify-between">
      <div class="px-32">
        <img src="@/assets/images/logo.png" alt="MoneyCookie" class="w-60" />
      </div>
      <div class="px-32">
        <ul class="flex space-x-10">
          <li>
            <router-link to="/">
              <button
                type="button"
                class="w-32 flex justify-center border-yellow-600 active:bg-yellow-600 border-2 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
              >
                로그아웃
              </button>
            </router-link>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>
