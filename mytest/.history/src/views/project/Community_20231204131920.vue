<template>
    <el-main style="height: 100%">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>鸣谢</span>
                    <el-button link class="button"><el-icon>
                            <More />
                        </el-icon></el-button>
                </div>
            </template>
            <el-image style="width: 400px; height: auto" :src="url" :fit="fit" />
        </el-card>
    </el-main>
</template>

<script>

import { firstRequest } from '../../axios/api'
export default {
    name: 'Community',
    data() {
        return {
            url: "https://z1.ax1x.com/2023/12/03/piy0L4O.jpg"
        }
    },
    methods: {

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

<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    width: 480px;
}
</style>