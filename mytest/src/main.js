import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './style.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import mitt from "mitt"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'highlight.js/styles/monokai-sublime.css'

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.config.globalProperties.$bus = new mitt();
app.use(store).use(router).use(ElementPlus).mount('#app')
