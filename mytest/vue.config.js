const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
})

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        //target 我们要连接的后端地址
        target: 'http://123.57.30.226:8081',
        changeOrigin: true, //是否跨域
        //pathRewrite: { '^/api': '' }
      },
    },
  },
}
module.exports = {
  //webpack配置
configureWebpack: {
    //关闭 webpack 的性能提示
    performance: {
      hints:false
    }
  }
}