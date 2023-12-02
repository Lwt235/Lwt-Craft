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
        onProxyRes(proxyRes, req, res) {
          //这里的req.url是经过路径重写后的url
               const realUrl = new URL(req.url || '', 'http://123.57.30.226:8081')?.href || '';
               proxyRes.headers['x-real-url1'] = realUrl;
          },
        //pathRewrite: { '^/api': '' }
      },
    },
  },
  configureWebpack: {
    //关闭 webpack 的性能提示
    performance: {
      hints:false
    }
  }
}