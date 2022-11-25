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
          class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400"
        />
        <input
          type="password"
          name="pw"
          id="pw"
          placeholder="비밀번호"
          class="text-sm px-4 py-3 rounded-lg w-full bg-gray-200 focus:bg-gray-100 border border-gray-200 focus:outline-none focus:border-yellow-400"
        />
        <button
          type="submit"
          name="login"
          id="login"
          class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-500"
        >
          로그인
        </button>
        <div class="text-right">
          <a href="#" class="text-yellow-600 hover:text-yellow-500"
            >비밀번호를 잊으셨나요?</a
          >
        </div>
        <div class="h-px bg-gray-100"></div>
        <button
          type="button"
          class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-500"
        >
          회원 가입
        </button>
      </form>
    </div>
  </div>
</template>

<script></script>
