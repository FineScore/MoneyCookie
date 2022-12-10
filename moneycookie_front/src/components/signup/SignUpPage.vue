<template>
  <div class="bg-gradient-to-t from-stone-400 via-yellow-400 to-yellow-600">
    <div
      class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
    >
      <div class="flex justify-center self-center z-10">
        <div class="p-12 bg-white mx-auto rounded-3xl w-[30rem]">
          <form class="space-y-6">
            <div class="space-y-2">
              <label for="nickname">닉네임 *</label>
              <input
                type="text"
                name="nickname"
                id="nickname"
                placeholder="닉네임을 입력해주세요."
                autocomplete="off"
                class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
              />
            </div>
            <div class="space-y-2">
              <label for="email">이메일 *</label>
              <div class="flex justify-between">
                <input
                  type="email"
                  name="email"
                  id="email"
                  placeholder="ID@example.com"
                  autocomplete="off"
                  class="w-[72.25%] text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                />
                <button
                  type="button"
                  class="w-[23.75%] flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer"
                >
                  인증
                </button>
              </div>
            </div>
            <div class="space-y-2">
              <label for="password-current">비밀번호 *</label>
              <input
                type="password"
                name="password-current"
                id="password-current"
                placeholder="영문, 숫자, 특수문자 2가지 조합 8 ~ 15자"
                class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
              />
            </div>
            <div class="space-y-2">
              <label for="password-confirm">비밀번호 확인 *</label>
              <input
                type="password"
                name="password-confirm"
                id="password-confirm"
                placeholder="비밀번호를 한 번 더 입력해주세요."
                class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
              />
            </div>
            <div class="h-px bg-gray-200"></div>
            <div>
              <router-link to="/">
                <button
                  type="submit"
                  class="w-full flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
                >
                  회원 가입
                </button>
              </router-link>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
