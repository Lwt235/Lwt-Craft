<template>
    <el-main style="height: 100%;">
        <h2>新面孔！欢迎欢迎~</h2>
        <table align="center">
            <tr>
                <td align="right">
                    <span style="font-size: 16px; margin-right: 10px">设置您的用户名:</span>
                </td>
                <td align="left">
                    <el-input size="small" v-model="name" placeholder="请输入用户名"
                        style="width:400px; height:30px; font-size:16px" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <span style="font-size: 16px; margin-right: 10px">设置您的密码:</span>
                </td>
                <td align="left">
                    <el-input type="password" size="small" v-model="password" placeholder="请输入密码"
                        style="width:400px; height:30px; font-size:16px" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <span style="font-size: 16px; margin-right: 10px">确认您的密码:</span>
                </td>
                <td align="left">
                    <el-input type="password" size="small" v-model="confirmPassword" placeholder="请输入密码"
                        style="width:400px; height:30px; font-size:16px" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <span style="font-size: 16px; margin-right: 10px">邮箱:</span>
                </td>
                <td align="left">
                    <el-input size="small" v-model="emailAddress" placeholder="请输入邮箱"
                        style="width:400px; height:30px; font-size:16px">
                        <template #append>
                            <el-button :disabled="!isAble" :loading="isLoading" @click="Send(emailAddress)">{{ Tag }}</el-button>
                        </template>
                    </el-input>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <span style="font-size: 16px; margin-right: 10px">验证码:</span>
                </td>
                <td align="left">
                    <el-input size="small" v-model="verifyCode" placeholder="请输入验证码"
                        style="width:400px; height:30px; font-size:16px" />
                </td>
            </tr>
        </table>
        <div style="margin: 20px">
            <el-button type="primary" round @click="this.$router.replace('/')">返回</el-button>
            <el-button type="primary" round @click="Confirm">确定</el-button>
        </div>
    </el-main>
</template>
  
<script>
import { sendCode } from '../../axios/api'
import { checkCode } from '../../axios/api'
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            name: '',
            password: '',
            confirmPassword: '',
            emailAddress: '',
            verifyCode: '',
            isAble: true,
            timeDelay: 0,
            clock: null,
            Tag: '发送验证码',
            isLoading: false
        }
    },
    methods: {
        async Confirm() {
            if (!this.name) {
                ElMessage({
                    showClose: true,
                    message: '用户名不能为空',
                    type: 'error'
                })
                return;
            }
            if (!this.password) {
                ElMessage({
                    showClose: true,
                    message: '密码不能为空',
                    type: 'error'
                })
                return;
            }
            if (this.password !== this.confirmPassword) {
                ElMessage({
                    showClose: true,
                    message: '两次输入的密码不同!',
                    type: 'error'
                })
                return;
            }
            if (!this.emailAddress) {
                ElMessage({
                    showClose: true,
                    message: '邮箱不能为空!',
                    type: 'error'
                })
                return;
            }
            if (!this.verifyCode) {
                ElMessage({
                    showClose: true,
                    message: '验证码不能为空!',
                    type: 'error'
                })
                return;
            }
            let res = await checkCode({
                verifyCode: this.verifyCode,
                email: this.emailAddress,
                name: this.name,
                password: this.password,
                authority: 'Guest'
            });
            //console.log(res,"/Check");
            if (res.code === 1) {
                ElMessage({
                    showClose: true,
                    message: '邮箱不存在',
                    type: 'error'
                })
                return;
            } else if (res.code === 2) {
                ElMessage({
                    showClose: true,
                    message: '验证码不匹配',
                    type: 'error'
                })
                return;
            } else if (res.code === 3) {
                ElMessage({
                    showClose: true,
                    message: '验证码过期啦，动作快一点哦',
                    type: 'error'
                })
                return;
            } else if (res.code === 0) {
                ElMessage({
                    showClose: true,
                    message: '注册成功',
                    type: 'success'
                })
                console.log(this.name, this.password);
                this.$router.replace('/');
            }

        },
        async Send(address) {
            this.isLoading = true;
            if(!address) {
                ElMessage({
                    showClose: true,
                    message: '邮箱不能为空',
                    type: 'error'
                })
                this.isLoading = false;
                return;
            }
            let res = await sendCode({
                email: address
            })
            this.isLoading = false;
            if (res.code === 2) {
                ElMessage({
                    showClose: true,
                    message: '邮箱格式不合法',
                    type: 'error'
                })
                return;
            } else if (res.code === 3) {
                ElMessage({
                    showClose: true,
                    message: '该邮箱已注册',
                    type: 'error'
                })
                return;
            } else {
                ElMessage({
                    showClose: true,
                    message: '已发送',
                    type: 'success'
                })
                let that = this;
                this.timeDelay = 60;
                this.isAble = false;
                this.clock = setInterval(() => {
                    that.timeDelay = that.timeDelay - 1;
                    that.Tag = that.timeDelay + '秒后可以重新发送';
                    if (that.timeDelay === 0) {
                        that.Tag = '重新发送';
                        that.clock = clearInterval(that.clock);
                        that.isAble = true;
                    }
                }, 1000)
            }
        }
    },
    mounted() {

    }
}
</script>

<style></style>