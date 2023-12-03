import request from './index'

//获取用户信息接口
export const firstRequest = () => request.get('/firstRequest')
export const list = () => request.get('/list')
export const getById = (data) => request.get('/getById', { params: data })
export const listBy = (data) => request.get('/listBy', { params: data })
export const getAuthority = () => request.get('/getAuthority')
export const getFileList = () => request.get('/getFileList')
export const checkAccount = (Data) => request.post('/checkAccount', { name: Data.name, password: Data.password })
export const Add = (Data) => request.post('/Add', { number: Data })
export const insert = (Data) => request.post('/insert', { msg: Data.msg, startTime: Data.startTime, endTime: Data.endTime })
export const update = (Data) => request.put('/update', { id: Data.id, msg: Data.msg, startTime: Data.startTime, endTime: Data.endTime })
export const _delete = (data) => request.delete('/delete', { params: data })
export const deleteByIds = (data) => request.delete('/deleteByIds', { params: data })
export const deleteFile = (data) => request.delete('/deleteFile', { params: data })

export function timestampToTime(timestamp) {
    let date = new Date(timestamp),
        Y = date.getFullYear() + '-',
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-',
        D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ',
        h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':',
        m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':',
        s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());

    return Y + M + D + h + m + s;
}
