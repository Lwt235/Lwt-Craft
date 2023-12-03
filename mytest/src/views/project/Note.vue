<template>
	<el-container class="layout-container-demo" style="height: 100%;">
		<el-main>
			<div class="demo-datetime-picker" style="margin-bottom: 20px;">
				<div class="block" style="display:flex;justify-content: space-between;">

					<!-- 时间选择器 -->
					<div>
						<el-date-picker v-model="DateTime" type="datetimerange" start-placeholder="开始时间"
							end-placeholder="结束时间" value-format="YYYY-MM-DD HH:mm:ss" date-format="YYYY/MM/DD ddd"
							time-format="A hh:mm:ss" />
					</div>

					<!-- 事件内容编辑器 -->
					<div>
						<el-input style="width: 300px;" v-model="todoMessage" placeholder="请输入事件内容" />
					</div>

					<!-- 提交按钮 -->
					<div>
						<el-button style="width:100px;" type="success" round @click="add">添加任务</el-button>
					</div>
				</div>
			</div>
			<el-table :data="List" stripe border style="height: 448px;">
				<el-table-column prop="startTime" label="开始时间" width="200" />
				<el-table-column prop="endTime" label="结束时间" width="200" />
				<el-table-column prop="editTime" label="编辑时间" width="200" />
				<el-table-column prop="msg" label="事件" />
				<el-table-column fixed="right" label="操作" width="150">
					<template #default="scope">

						<!-- 编辑数据 -->
						<el-button link @click="edit(scope.row.id); dialogVisible = true"><el-icon>
								<EditPen />
							</el-icon></el-button>

						<!-- 删除数据 -->
						<el-button link style="margin-left: 20px;" @click="del(scope.row.id)"><el-icon class="icon-delete">
								<Delete />
							</el-icon></el-button>
					</template>
				</el-table-column>
			</el-table>

			<el-dialog v-model="dialogVisible" title="编辑" width="500px" :before-close="handleClose">
				<!-- 内容 -->
				<!-- 事件内容 -->
				<div style="margin: 20px;">
					<span style="font-size: 16px; margin-right: 10px">事件内容:</span>
					<el-input size="small" v-model="editItem.msg" :suffix-icon="Message"
						style="width:auto; height:30px; font-size:16px" />
				</div>

				<!-- 最新修改时间 -->
				<div style="margin: 20px;">
					<span style="font-size: 16px; margin-right: 10px">上次修改时间:</span>
					<el-input size="small" v-model="editItem.editTime" disabled :suffix-icon="Calendar"
						style="width:auto; height:30px; font-size:16px" />
				</div>

				<!-- 时间选择器 -->
				<div style="margin-top:10px">
					<el-date-picker v-model="editDateTime" type="datetimerange" start-placeholder="开始时间"
						end-placeholder="结束时间" value-format="YYYY-MM-DD HH:mm:ss" date-format="YYYY/MM/DD ddd"
						time-format="A hh:mm:ss" />
				</div>

				<!-- 页脚 -->
				<template #footer>
					<span class="dialog-footer">
						<el-button @click="dialogVisible = false">取消</el-button>
						<el-button type="primary" @click="_update(); dialogVisible = false">确定</el-button>
					</span>
				</template>
			</el-dialog>
		</el-main>
	</el-container>
</template>

<script setup>
import { ref } from 'vue'
import { Calendar, Search, Message } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
const dialogVisible = ref(false)
</script>

<script>
import { firstRequest } from '../../axios/api'
import { list } from '../../axios/api'
import { getById } from '../../axios/api'
import { listBy } from '../../axios/api'
import { insert } from '../../axios/api'
import { update } from '../../axios/api'
import { _delete } from '../../axios/api'
import { deleteByIds } from '../../axios/api'
import { timestampToTime } from '../../axios/api';
import { ElMessageBox } from 'element-plus'

const handleClose = (done) => {
	ElMessageBox.confirm('放弃编辑?')
		.then(() => {
			done();
		})
		.catch(() => {
			// catch error
		})
}
export default {
	name: 'userInfor',
	data() {
		return {
			List: [],
			DateTime: [],
			todoMessage: '',
			editItem: {},
			editDateTime: []
		}
	},
	methods: {
		async del(id) {
			//console.log(id)
			let res = await _delete({ id });
			if (res.code === 1) {
				ElMessage({
					showClose: true,
					message: '只有管理员可以修改',
					type: 'error',
				})
			}
			else {
				this.List = this.List.filter(item => item.id !== id);
				//console.log(res,'/delete');
			}
		},
		async add() {

			//console.log(this.DateTime,'DATETIME');
			if (this.DateTime.length === 0) {
				alert('请输入起止时间');
				return;
			}
			if (this.todoMessage === '') {
				alert('请输入任务名称');
				return;
			}
			//console.log('send:'+this.todomsg+Datetime);
			let res = await insert({
				msg: this.todoMessage,
				startTime: this.DateTime[0],
				endTime: this.DateTime[1]
			})
			//console.log(res,'/insert');
			this.todoMessage = '';
			this.DateTime[0] = '';
			this.DateTime[1] = '';
			if (res.code === 1) {
				ElMessage({
					showClose: true,
					message: '只有管理员可以修改',
					type: 'error',
				})
			}
			else {
				this.$router.replace("/empty_note");
			}
		},
		async edit(id) {
			//console.log("!"+id);
			let res = await getById({ id });
			let result = JSON.parse(res.data);
			//console.log(result,"/edit")
			result.startTime = timestampToTime(result.startTime);
			result.endTime = timestampToTime(result.endTime);
			result.editTime = timestampToTime(result.editTime);
			this.editItem = result;
			this.editDateTime[0] = result.startTime;
			this.editDateTime[1] = result.endTime;
			//console.log(this.editItem);
		},
		// async clear() {
		// 	console.log("清空")
		// 	this.List=[];
		// 	let res = await clearData()
		// }
		async _update() {
			//console.log(this.editItem,this.editDateTime,"update!")
			let res = await update({
				id: this.editItem.id,
				msg: this.editItem.msg,
				startTime: this.editDateTime[0],
				endTime: this.editDateTime[1]
			})
			if (res.code === 1) {
				ElMessage({
					showClose: true,
					message: '只有管理员可以修改',
					type: 'error',
				})
			}
			else {
				//console.log(res, '/update');
				this.$router.replace("/empty_note");
			}
		}
	},

	created() {
		let res = list()
		//console.log(res, '初始化,获取的用户信息')
		res.then((result) => {
			if (result.code === 0) {
				//console.log("result",JSON.parse(result.data));
				this.List = JSON.parse(result.data);
				this.List.forEach(item => {
					item.startTime = timestampToTime(item.startTime);
					item.endTime = timestampToTime(item.endTime);
					item.editTime = timestampToTime(item.editTime);
				});
			}
		})
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
 
<style scoped>
.dialog-footer button:first-child {
	margin-right: 10px;
}
</style>

<style>
.layout-container-demo .el-main {
	padding: 0;
}

.dialog-footer button:first-child {
	margin-right: 10px;
}

.icon-delete:hover {
	color: red;
}
</style>