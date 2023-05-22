const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // publicPath : "./",
  transpileDependencies: true,
  devServer: {
    port: 8082,
    proxy: {
      '/api': {
        target: 'http://localhost:1095',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }

})
