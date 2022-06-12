const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: 'warning',
  publicPath: './',
  outputDir: 'static',
  devServer: {
    proxy: 'http://localhost:8888'
  }
})
