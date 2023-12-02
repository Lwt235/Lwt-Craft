<template>
    <el-main style="height: 100%">
        <h2>欢迎！您可以先在此处验证请求是否正常</h2>
        <h1>{{ msg }}</h1>
        <el-button type="primary" plain @click="Request">请求当前时间</el-button>
    </el-main>
</template>

<script>
import { firstRequest } from '../../axios/api'
export default {
    name: 'Test',
    data() {
        return {
            msg: '',
        }
    },
    methods: {
        async Request() {
            let res = firstRequest();
            res.then((result) => {
                //console.log("test:" + result);
                this.msg = result.data;
            })

        }
    },
    mounted() {
        let res = firstRequest();
        res.then((result) => {
            //console.log("res:", result);
            if (result.code === 1) {
                this.$router.push({ path: "/" }).then(() => {
                    location.reload()
                })
            }
        })
    }
}
</script>

<style></style>