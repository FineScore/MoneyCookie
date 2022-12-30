<template>
  <div class="flex justify-center self-center z-10">
    <div class="p-12 bg-white mx-auto rounded-3xl w-96">
      <div class="flex justify-center items-center flex-col space-y-6">
        <router-link to="/portfolio">
          <button
            type="button"
            class="w-72 h-14 flex items-center bg-[#03C75A] rounded"
          >
            <img
              src="@/assets/images/btnG_circle.png"
              alt="네이버 로그인"
              class="ml-2 w-14"
            />
            <p class="ml-10 text-lg text-white font-bold">네이버 로그인</p>
          </button>
        </router-link>
        <button
          type="button"
          class="w-72 h-14 flex items-center bg-[#FEE500] rounded"
        >
          <img
            src="@/assets/images/kakao_logo.png"
            alt="카카오 로그인"
            class="ml-2 w-14"
          />
          <p class="ml-10 text-lg font-bold">카카오 로그인</p>
        </button>
      </div>
    </div>
  </div>
</template>

<script></script>
