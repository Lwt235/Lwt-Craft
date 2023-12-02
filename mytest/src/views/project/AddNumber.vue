<template>
    <el-main style="height: 100%">
        <el-input v-model="num" style="width: 200px;"></el-input>
        <h1>{{ num }}</h1>
        <el-button type="primary" plain @click="numAdd(num)">数字加一</el-button>
    </el-main>
</template>

<script>
import { Add } from '../../axios/api'
import { firstRequest } from '../../axios/api';
export default {
    name: 'AddNumber',
    data() {
        return {
            num: 1
        }
    },
    methods: {
        async numAdd(num) {
            //console.log("num:" + num);
            let res = await Add(num)
            //console.log(res, "/Add")
            this.num = res.data;
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