<template>
    <el-main style="height: 100%;">
        <h2>欢迎访问本系统，请进行身份校验</h2>
        <div style="margin: 20px;">
            <span style="font-size: 16px; margin-right: 10px">用户名:</span>
            <el-input size="small" v-model="name" placeholder="请输入用户名" style="width:auto; height:30px; font-size:16px" />
        </div>
        <div style="margin: 20px;">
            <span style="font-size: 16px; margin-right: 10px">密码:</span>
            <el-input type="password" size="small" v-model="password" placeholder="请输入密码" style="width:auto; height:30px; font-size:16px" />
        </div>
        <el-button type="primary" round @click="Check">登录</el-button>
    </el-main>
</template>
  
<script>
import { checkAccount } from "../../axios/api"
import { firstRequest } from "../../axios/api"
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            name: '',
            password: ''
        }
    },
    methods: {
        Load() {
            this.$bus.emit('App', true)
        },
        async Check() {
            let res = await checkAccount({
                name: this.name,
                password: this.password
            });
            //console.log(res,"/Check");
            if(res.message === "Success") {
                ElMessage({
                    showClose: true,
                    message: '登陆成功',
                    type: 'success',
                })
                //console.log("setToken:",res.data);
                localStorage.setItem("token",res.data);
                this.Load();
            }
            else if(res.message === "AccountNotFound")
            {
                ElMessage({
                    showClose: true,
                    message: '用户名不存在，请检查后重新输入',
                    type: 'error',
                })
            }
            else if(res.message === "WrongPassword")
            {
                ElMessage({
                    showClose: true,
                    message: '密码错误，请检查后重新输入',
                    type: 'error',
                })
            }
        }
    },
    mounted() {
        if(localStorage.getItem("token") != null)
        {
            let res = firstRequest();
            res.then((result) => {
                if(result.code === 0)
                {
                    ElMessage({
                        showClose: true,
                        message: '您已登录',
                        type: 'success',
                    })
                    this.$router.replace("/Test");
                }
            })
        }
    }
}
</script>

<style>

</style>