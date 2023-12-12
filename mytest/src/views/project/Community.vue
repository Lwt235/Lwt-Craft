<template>
    <el-main style="height: 100%">
        <div style="display: flex;">
            <div style="display: flex;flex-direction:column">
                <div v-for="(item, index) in CommunityInformation">
                    <div v-if="index % 3 == 0">
                        <el-card class="box-card" shadow="hover"
                            style="border-radius: 20px; background-color: rgb(255, 254, 253); margin: 10px; width: 420px">
                            <template #header>
                                <div class="card-header">
                                    <div>
                                        <el-image
                                            style="vertical-align: middle; width: 50px; height: 50px; border-radius: 100%"
                                            :src="item.avatarPath" />
                                        <span style="vertical-align: middle; margin-left: 10px;">{{ item.nickname }}</span>
                                    </div>
                                    <el-button link class="button"><el-icon>
                                            <More />
                                        </el-icon></el-button>
                                </div>
                            </template>
                            <h2>{{ item.title }}</h2>
                            <div class="w-e-text" v-html="item.detailedInformation"></div>
                            <!-- <el-image style="width: 400px; height: auto" src="https://lwt-server.cn/file/download/pictureFromOP.png" /> -->
                        </el-card>
                    </div>
                </div>
            </div>
            <div style="display: flex;flex-direction:column">
                <div v-for="(item, index) in CommunityInformation">
                    <div v-if="index % 3 == 1">
                        <el-card class="box-card" shadow="hover"
                            style="border-radius: 20px; background-color: rgb(255, 254, 253); margin: 10px; width: 420px">
                            <template #header>
                                <div class="card-header">
                                    <div>
                                        <el-image
                                            style="vertical-align: middle; width: 50px; height: 50px; border-radius: 100%"
                                            :src="item.avatarPath" />
                                        <span style="vertical-align: middle; margin-left: 10px;">{{ item.nickname }}</span>
                                    </div>
                                    <el-button link class="button"><el-icon>
                                            <More />
                                        </el-icon></el-button>
                                </div>
                            </template>
                            <h2>{{ item.title }}</h2>
                            <div class="w-e-text" v-html="item.detailedInformation"></div>
                            <!-- <el-image style="width: 400px; height: auto" src="https://lwt-server.cn/file/download/pictureFromOP.png" /> -->
                        </el-card>
                    </div>
                </div>
            </div>
            <div style="display: flex;flex-direction:column">
                <div v-for="(item, index) in CommunityInformation">
                    <div v-if="index % 3 == 2">
                        <el-card class="box-card" shadow="hover"
                            style="border-radius: 20px; background-color: rgb(255, 254, 253); margin: 10px; width: 420px">
                            <template #header>
                                <div class="card-header">
                                    <div>
                                        <el-image
                                            style="vertical-align: middle; width: 50px; height: 50px; border-radius: 100%"
                                            :src="item.avatarPath" />
                                        <span style="vertical-align: middle; margin-left: 10px;">{{ item.nickname }}</span>
                                    </div>
                                    <el-button link class="button"><el-icon>
                                            <More />
                                        </el-icon></el-button>
                                </div>
                            </template>
                            <h2>{{ item.title }}</h2>
                            <div class="w-e-text" v-html="item.detailedInformation"></div>
                            <!-- <el-image style="width: 400px; height: auto" src="https://lwt-server.cn/file/download/pictureFromOP.png" /> -->
                        </el-card>
                    </div>
                </div>
            </div>
        </div>

        <el-button @click="handleAdd()">Add</el-button>
        <el-dialog v-model="dialogVisible" title="编辑" width="1000px" :before-close="handleClose" @close="closeDialog">
            <!-- 内容 -->
            <!-- 昵称 -->
            <tr>
                <td>
                    <span style="font-size: 16px; margin-right: 10px">昵称:</span>
                </td>
                <td align="left">
                    <el-input size="small" v-model="editItem.nickname" style="width:auto; height:30px; font-size:16px">
                        <template #suffix>
                            <el-icon>
                                <user />
                            </el-icon>
                        </template>
                    </el-input>
                </td>
            </tr>

            <!-- 头像 -->
            <tr>
                <td style="display:table-cell; vertical-align:middle">
                    <span style="font-size: 16px; margin-right: 10px; width:auto">头像:</span>
                </td>
                <td align="left">
                    <el-upload class="avatar-uploader" ref="upload" :headers="{ token: userToken }"
                        action="/community/upload" list-type="picture" :show-file-list="false" :auto-upload="false"
                        :on-change="imgSaveToUrl" :on-success="handleSuccess" :before-upload="beforeUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar" style="width: 50px; height: 50px" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>

                </td>
            </tr>

            <tr style="color:gray">
                <td align="left">
                    <span>
                        提示:
                    </span>
                </td>
                <td align="left">
                    <span>
                        可支持PNG、JPG/JPEG、WEBP，图片大小不超过2M，图片会被变形成正方形
                    </span>
                </td>
            </tr>
            <!-- 标题 -->
            <tr>
                <td>
                    <span style="font-size: 16px; margin-right: 10px">标题:</span>
                </td>
                <td align="left">
                    <el-input size="small" v-model="editItem.title" style="width:auto; height:30px; font-size:16px">
                        <template #suffix>
                            <el-icon>
                                <CollectionTag />
                            </el-icon>
                        </template>
                    </el-input>
                </td>
            </tr>

            <!-- 编辑器 -->
            <div style="margin: 10px;" id="editor"></div>

            <!-- 页脚 -->
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleUpdate();">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <h3>局域网ip：{{ IP }}</h3>
    </el-main>
</template>

<script>

import { firstRequest } from '../../axios/api'
import { getCommunityList } from '../../axios/api'
import { communityInsert } from '../../axios/api'
import { ElMessageBox, ElMessage } from 'element-plus'
import { timestampToTime } from '../../axios/api';
import { getAuthority } from '../../axios/api';
import E from 'wangeditor'
import hljs from 'highlight.js'

export default {
    name: 'Community',
    data() {
        return {
            userToken: '',
            editor: null,
            CommunityInformation: [],
            dialogVisible: (false),
            editItem: {},
            imageUrl: '',
            IP: ''
        }
    },
    methods: {
        imgSaveToUrl(event) {
            let file = event.raw;
            let isJPG = (file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/webp' || file.type === 'image/png');
            let isLt2M = (file.size / 1024 / 1024 < 2);
            if (!isJPG) {
                ElMessage.error('上传头像图片只能是 jgp,jpeg,webp,png格式!');
                return;
            }
            if (!isLt2M) {
                ElMessage.error('上传头像图片大小不能超过 2MB!');
                return
            }
            // 获取上传图片的本地URL，用于上传前的本地预览
            let URL = null;
            this.image = file;
            if (window.createObjectURL != undefined) {
                // basic
                URL = window.createObjectURL(event.raw);
            } else if (window.URL != undefined) {
                // mozilla(firefox)
                URL = window.URL.createObjectURL(event.raw);
            } else if (window.webkitURL != undefined) {
                // webkit or chrome
                URL = window.webkitURL.createObjectURL(event.raw);
            }
            // 转换后的地址为 blob:http://xxx/7bf54338-74bb-47b9-9a7f-7a7093c716b5
            this.imageUrl = URL;
            // console.log(this.image,this.imageUrl);
        },
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            console.log(res, this.imageUrl);
        },
        handleAdd() {
            this.dialogVisible = true;
            this.$nextTick(() => {
                this.editor = new E("#editor");
                this.editor.highlight = hljs;
                this.editor.create();
            })
        },
        closeDialog() {
            this.editor.destroy();
            this.editor = null;
        },
        async getList() {
            let res = await getCommunityList();
            if (res.code === 0) {
                // console.log(res.data);
                this.CommunityInformation = JSON.parse(res.data);
            }
        },
        handleClose(done) {
            ElMessageBox.confirm('放弃编辑?')
                .then(() => {
                    this.editItem = {};
                    done();
                })
                .catch(() => {
                    // catch error
                })
        },
        beforeUpload(file) {
            if (!this.editItem.nickname) {
                alert('请输入昵称');
                return;
            }
            if (!file) {
                alert('请选择一张图片作为头像');
                return;
            }
            if (!this.editItem.title) {
                alert('请输入标题');
                return;
            }
            if (!this.editor.txt.html()) {
                alert('请输入内容');
                return;
            }
            if (!this.editItem.linkPath) {
                this.editItem.linkPath = "";
            }
            this.editItem.time = timestampToTime(new Date());
            this.editItem.detailedInformation = this.editor.txt.html();
            this.imageUrl = "https://lwt-server.cn/community/download/" + file.name;
        },
        async handleSuccess() {
            let res = await communityInsert({
                nickname: this.editItem.nickname,
                avatarPath: this.imageUrl,
                title: this.editItem.title,
                detailedInformation: this.editItem.detailedInformation,
                linkPath: this.editItem.linkPath,
                time: this.editItem.time
            })
            this.editItem = {};
            this.imageUrl = '';
            if (res.code === 0) {
                this.$router.replace("/empty_community");
			}
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
        handleUpdate() {
            // 手动触发文件上传
            this.checkAuthority();
            this.$refs.upload.submit();
            this.dialogVisible = false;
        }
    },
    created() {
        this.getList();
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
        if (localStorage.getItem("token") != null) {
            this.userToken = localStorage.getItem("token");
            // console.log('token!: ', this.userToken);
        }
        if (localStorage.getItem("x-real-ip") != null) {
            this.IP = localStorage.getItem("x-real-ip");
            console.log('IP!: ', this.IP);
        }
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



.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 50px;
    height: 50px;
    text-align: center;
}
</style>