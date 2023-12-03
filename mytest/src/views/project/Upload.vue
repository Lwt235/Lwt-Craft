<template>
    <el-main style="height: 100%;padding: 0">
        <div style="height: 424px;">
            <h3>已上传文件列表</h3>
            <el-table :data="fileList" stripe border style="height: 375px;">
                <el-table-column prop="name" label="文件名" />
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">
                        <!-- 下载文件 -->
                        <el-button link @click="download(scope.row.url); dialogVisible = true"><el-icon>
                                <Download />
                            </el-icon></el-button>

                        <!-- 删除文件 -->
                        <el-button link style="margin-left: 20px;" @click="del(scope.row.url)"><el-icon class="icon-delete">
                            <Delete />
                            </el-icon></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-divider />
        <div style="height: 210px;">
            <el-upload drag action="/api/upload" :headers="{ token: userToken }" :on-success="onHandleFile"
                :show-file-list="false" multiple>
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    将文件拖至此或 <em>点击上传</em>
                </div>
                <template #tip>
                    <div class="el-upload__tip">
                        上传的文件不应超过 1MB
                    </div>
                </template>
            </el-upload>
        </div>
    </el-main>
</template>

<script setup>
import { UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
</script>

<script>
import { firstRequest } from '../../axios/api';
import { getFileList } from '../../axios/api';
import { deleteFile } from '../../axios/api';
import { getAuthority } from '../../axios/api';
export default {
    name: 'Upload',
    data() {
        return {
            fileList: [],
            userToken: ""
        }
    },
    methods: {
        download(url) {
            window.open(url);
        },
        getFiles() {
            let res = getFileList()
            //console.log(res, '初始化,获取的用户信息')
            res.then((result) => {
                if (result.code === 0) {
                    //console.log("result",JSON.parse(result.data));
                    this.fileList = JSON.parse(result.data);
                }
            })
        },
        del(url) {
            let res = deleteFile({ url });
            res.then((result) => {
                if (result.code === 0) {
                    this.getFiles();
                    ElMessage({
                        showClose: true,
                        message: '删除成功',
                        type: 'success',
                    })
                } else {
                    ElMessage({
                        showClose: true,
                        message: '只有管理员可以删除文件哦',
                        type: 'error',
                    })
                }
            })
        },
        checkAuthority() {
            let res = getAuthority();
            res.then((result) => {
                if (result.code === 0) {
                    if (result.data != "Administrator") {
                        ElMessage({
                            showClose: true,
                            message: '只有管理员可以上传文件哦',
                            type: 'error',
                        })
                    }
                }
            })
        },
        onHandleFile() {
            this.checkAuthority();
            this.getFiles();
        }
    },
    mounted() {
        this.getFiles();
        let res = firstRequest();
        res.then((result) => {
            //console.log("res:", result);
            if (result.code === 1) {
                this.$router.push({ path: "/" }).then(() => {
                    location.reload()
                })
            }
        })
        if (localStorage.getItem("token") != null) {
            this.userToken = localStorage.getItem("token");
            console.log('token!: ', this.userToken);
        }
    },
    created() {
        this.getFiles();
    }
}
</script>

<style>
.icon-delete:hover {
	color: red;
}
</style>