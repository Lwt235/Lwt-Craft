<template>
    <el-main style="height: 100%">
        <div style="display: flex;">
            <div v-for="item in CommunityInformation">
                <el-card class="box-card" shadow="hover" style="border-radius: 20px; background-color: rgb(255, 254, 253); margin: 10px">
                    <template #header>
                        <div class="card-header">
                            <div>
                                <el-image style="vertical-align: middle; width: 50px; border-radius: 100%" :src="item.avatarPath" />
                                <span style="vertical-align: middle; margin-left: 10px;">{{ item.nickname }}</span>
                            </div>
                            <el-button link class="button"><el-icon>
                                <More />
                            </el-icon></el-button>
                        </div>
                    </template>
                    <span>{{ item.title }}</span>
                    <div>
                        {{ item.detailedInformation }}
                    </div>
                    <!-- <el-image style="width: 400px; height: auto" src="https://lwt-server.cn/file/download/pictureFromOP.png" /> -->
                </el-card>
            </div>
        </div>
    </el-main>
</template>

<script>

import { firstRequest } from '../../axios/api'
export default {
    name: 'Community',
    data() {
        return {
            CommunityInformation: []
        }
    },
    methods: {

    },
    created() {
        this.CommunityInformation.push({ nickname: "Lwt235", avatarPath: "https://lwt-server.cn/file/download/Lwt235.jpg", title: "鸣谢", detailedInformation: "Test" })
        this.CommunityInformation.push({ nickname: "Lwt235", avatarPath: "https://lwt-server.cn/file/download/Lwt235.jpg", title: "鸣谢", detailedInformation: "Test" })
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