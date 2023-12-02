import Axios from 'axios'
const instance = Axios.create({
	baseURL: '/api'
})

instance.interceptors.request.use((config) => {
	if (localStorage.getItem("token") != null) {
		//console.log("getToken:", localStorage.getItem("token"));
		config.headers.token = localStorage.getItem("token");;
	}
	if (config.method === 'post' || config.method === 'put') {
		//console.log("data:" + config.data)
		let data = ''
		for (let item in config.data) {
			if (config.data[item])
				data += encodeURIComponent(item) + '=' + encodeURIComponent(config.data[item]) + '&'
		}
		config.data = data.slice(0, data.length - 1)
	}
	console.log(config, '发送请求前config信息')
	return config
}, err => {
	return Promise.reject(err)
})

instance.interceptors.response.use((res) => {
	console.log('接受的数据',res.data)
	return res.data
}, err => {
	return Promise.reject(err)
})

export default instance