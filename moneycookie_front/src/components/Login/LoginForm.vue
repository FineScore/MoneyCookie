<template>
  <div class="flex justify-center self-center z-10">
    <div class="p-12 bg-white mx-auto rounded-3xl w-96">
      <form class="space-y-6">
        <input
          type="text"
          name="email"
          id="email"
          placeholder="이메일"
          autocomplete="off"
          class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        />
        <input
          type="password"
          name="pw"
          id="pw"
          placeholder="비밀번호"
          class="text-sm px-4 py-3 rounded-lg w-full bg-gray-200 focus:bg-gray-100 border border-gray-200 focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        />
        <div>
          <router-link to="/select">
            <button
              type="submit"
              name="login"
              id="login"
              class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200"
            >
              로그인
            </button>
          </router-link>
        </div>
        <div>
          <div class="flex items-center">
            <input
              type="checkbox"
              name="auto-login"
              id="auto-login"
              class="h-3.5 w-3.5 rounded"
            />
            <label for="auto-login" class="ml-2 text-sm"
              >로그인 상태 유지</label
            >
          </div>
          <div class="text-right">
            <a href="#" class="text-yellow-600 hover:text-yellow-500 text-xs"
              >비밀번호를 잊으셨나요?</a
            >
          </div>
        </div>
        <div class="h-px bg-gray-200"></div>
        <div>
          <router-link to="/signup">
            <button
              type="button"
              class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200"
            >
              회원 가입
            </button>
          </router-link>
        </div>
        <div class="flex justify-center items-center">
          <button type="button">
            <img
              src="@/assets/images/btnG_circle.png"
              alt="네이버 로그인"
              class="w-12"
            />
          </button>
          <button type="button">
            <img
              src="@/assets/images/kakao_logo.png"
              alt="카카오 로그인"
              class="w-12 rounded-full ml-4"
            />
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script></script>
