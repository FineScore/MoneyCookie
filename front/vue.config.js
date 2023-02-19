const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  outputDir: "../back/src/main/resources/static",
  transpileDependencies: true,
  devServer: {
    historyApiFallback: true,
    proxy: {
      "/": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
