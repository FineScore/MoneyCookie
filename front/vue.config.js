const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  outputDir: "../back/src/main/resources/static",
  devServer: {
    port: 3000,
    historyApiFallback: true,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
});
