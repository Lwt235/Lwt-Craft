<template>
    <el-main style="height: 100%;">
        <h2>常见问题</h2>
        <el-table :data="Items" stripe style="width: 1000px; margin:auto" height="300">
            <el-table-column prop="question" label="问题" width="300" />
            <el-table-column prop="answer" label="解答" width="700" />
        </el-table>
        <div style="margin: 20px">
            <el-button link type="primary" round @click="this.$router.replace('/')">返回</el-button>
        </div>
    </el-main>
</template>
  
<script>
import { getQuestions } from '../../axios/api'
export default {
    data() {
        return {
            Items: []
        }
    },
    methods: {
        Confirm() {

        }
    },
    create() {
    },
    mounted() {
        let res = getQuestions();
        res.then((result) => {
            if (result.code === 0) {
                this.Items = JSON.parse(result.data);
            }
        })
    }
}
</script>

<style>
</style>