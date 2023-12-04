import request from './index'

//获取用户信息接口
//basic:
export const firstRequest = () => request.get('/basic/firstRequest')
export const getAuthority = () => request.get('/basic/getAuthority')
export const Add = (Data) => request.post('/basic/Add', { number: Data })
export const checkAccount = (Data) => request.post('/basic/checkAccount', { name: Data.name, password: Data.password })

//calendar:
export const list = () => request.get('/calendar/list')
export const getById = (data) => request.get('/calendar/getById', { params: data })
export const listBy = (data) => request.get('/calendar/listBy', { params: data })
export const insert = (Data) => request.post('/calendar/insert', { msg: Data.msg, startTime: Data.startTime, endTime: Data.endTime })
export const update = (Data) => request.put('/calendar/update', { id: Data.id, msg: Data.msg, startTime: Data.startTime, endTime: Data.endTime })
export const _delete = (data) => request.delete('/calendar/delete', { params: data })
export const deleteByIds = (data) => request.delete('/calendar/deleteByIds', { params: data })

//file:
export const getFileList = () => request.get('/file/getFileList')
export const deleteFile = (data) => request.delete('/file/deleteFile', { params: data })

//community:
export const getCommunityList = () => request.get('/community/list')

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
