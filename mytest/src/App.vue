<template>
    <!-- <nav> -->
    <!-- <router-link :to="'/'+Path"></router-link> -->
    <!-- <router-link to="/about">About</router-link> -->
    <!-- </nav> -->
    <!-- <router-view/> -->
    <h1>Lwt的创客空间</h1>
    <el-container>
        <el-aside v-show="isLoad" width="200px" style="background-color: lightblue;">
            <h4>当前权限:{{ Authority }}</h4>
            <el-menu default-active="this.$route.path" router>

                <el-menu-item index="/Test"><el-icon>
                        <location />
                    </el-icon>测试请求</el-menu-item>
                <el-menu-item index="/AddNumber"><el-icon>
                        <icon-menu />
                    </el-icon>数字增加</el-menu-item>
                <el-menu-item index="/Note"><el-icon>
                        <document />
                    </el-icon>备忘录</el-menu-item>
                <el-button type="danger" round style="margin:20px" @click="exit">登出</el-button>

            </el-menu>
        </el-aside>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
    <a href="https://beian.mps.gov.cn/#/query/webSearch?code=43010402001517" rel="noreferrer" target="_blank" style="text-decoration: none; color:black">湘公网安备43010402001517</a>
    <br>
    <img src="./assets/beian.png" width="36" height="40">
    <a href="https://beian.miit.gov.cn/" target="_blank" style="text-decoration: none; color:black">湘ICP备2023030377号-1</a>
</template>

<script setup>
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getAuthority } from './axios/api';
</script>

<script>
// 解决ERROR ResizeObserver loop completed with undelivered notifications.问题的

const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}

// 解决ERROR ResizeObserver loop completed with undelivered notifications.问题的

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}

export default {
    data() {
        return {
            isLoad: false,
            Authority: ""
        }
    },
    created() {
        console.log("开始监听")
        this.$bus.on('App', (val) => {
            console.log("接受")
            let res = getAuthority();
            res.then((result) => {
                this.Authority = result.data;
            })
            this.isLoad = val;
            this.$router.replace('/Test');
        })
        
    },
    mounted() {
        // 监听 resize 方法
        //console.log("App_mounted!");
        
        window.addEventListener("resize", this.renderResize, false)
        if (localStorage.getItem("token") != null) {
            this.isLoad = true;
        }
        else {
            this.isLoad = false;
        }
    },
    methods: {
        _isMobile() {
            let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
            return flag;
        },
        renderResize() {
            // 判断横竖屏
            let width = document.documentElement.clientWidth
            let height = document.documentElement.clientHeight
            if (width <= height && this._isMobile()) {
                //alert("建议横屏访问哦");
                ElMessage({
                    grouping: true,
                    showClose: true,
                    message: '建议横屏使用哦'
                })
            }
            // 做页面适配

        },
        exit() {
            this.isLoad = false;
            localStorage.removeItem("token");
            this.$router.replace("/")
        }
    },
    beforeDestroy() {
        // 移除监听
        window.removeEventListener("resize", this.renderResize, false)
    }
}
</script>