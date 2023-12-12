<template>
    <el-main style="height: 100%;">
        <h2>{{ title }}</h2>
        <table align="center">
            <div v-show="isCheck">
                <tr>
                    <td align="right">
                        <span style="font-size: 16px; margin-right: 10px">用户名:</span>
                    </td>
                    <td align="left">
                        <el-input disabled size="small" v-model="originInformation.name"
                            style="width:400px; height:30px; font-size:16px" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span style="font-size: 16px; margin-right: 10px">设置您的新密码:</span>
                    </td>
                    <td align="left">
                        <el-input type="password" size="small" v-model="newPassword" placeholder="请输入密码"
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
            </div>
            <div v-show="!isCheck">
                <tr>
                    <td align="right">
                        <span style="font-size: 16px; margin-right: 10px">邮箱:</span>
                    </td>
                    <td align="left">
                        <el-input size="small" v-model="emailAddress" placeholder="请输入账号绑定的邮箱"
                            style="width:400px; height:30px; font-size:16px">
                            <template #append>
                                <el-button :disabled="!isAble" :loading="isLoading" @click="Send(emailAddress)">{{ Tag
                                }}</el-button>
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
            </div>
        </table>
        <div style="margin: 20px">
            <el-button type="primary" round @click="this.$router.replace('/')">返回</el-button>
            <el-button v-show="!isCheck" type="primary" round @click="Confirm">确定</el-button>
            <el-button v-show="isCheck" type="primary" round @click="Update">提交</el-button>
        </div>
    </el-main>
</template>
  
<script>
import { ElMessage } from 'element-plus'
import { resetSend, resetCheck, resetPassword } from '../../axios/api'
export default {
    data() {
        return {
            newPassword: '',
            confirmPassword: '',
            emailAddress: '',
            verifyCode: '',
            isAble: true,
            timeDelay: 0,
            clock: null,
            Tag: '发送验证码',
            isLoading: false,
            originInformation: {},
            isCheck: false,
            title: "欸，记不清了吗，那只好重置密码喽"
        }
    },
    methods: {
        async Confirm() {
            if (!this.verifyCode) {
                ElMessage({
                    showClose: true,
                    message: '验证码不能为空!',
                    type: 'error'
                })
                return;
            }
            let res = await resetCheck({
                email: this.emailAddress,
                verifyCode: this.verifyCode,
                password: this.password
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
                    message: '成功',
                    type: 'success'
                })
            }
            this.isCheck = true;
            this.title = "这次别再忘了噢"
        },
        async Update() {
            if(this.newPassword !== this.confirmPassword) {
                ElMessage({
                    showClose: true,
                    message: '两次输入的密码不同!',
                    type: 'error'
                })
                return;
            }
            let res = await resetPassword({
                email: this.emailAddress,
                verifyCode: this.verifyCode,
                password: this.newPassword
            })
            if (res.code === 0) {
                ElMessage({
                    showClose: true,
                    message: '修改成功',
                    type: 'success'
                })
                this.isCheck = false;
                this.$router.replace("/");
            }
            
        },
        async Send(address) {
            this.isLoading = true;
            if (!address) {
                ElMessage({
                    showClose: true,
                    message: '邮箱不能为空',
                    type: 'error'
                })
                this.isLoading = false;
                return;
            }
            let res = await resetSend({
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
                    message: '该邮箱未注册',
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
                this.originInformation = JSON.parse(res.data);
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