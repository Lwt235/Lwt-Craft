import request from './index'

//获取用户信息接口
//basic:
export const firstRequest = () => request.get('/basic/firstRequest')
export const getAuthority = () => request.get('/basic/getAuthority')
export const Add = (Data) => request.post('/basic/Add', Data)
export const checkAccount = (Data) => request.post('/basic/checkAccount', Data)

//calendar:
export const list = () => request.get('/calendar/list')
export const getById = (data) => request.get('/calendar/getById', { params: data })
export const listBy = (data) => request.get('/calendar/listBy', { params: data })
export const calendarInsert = (Data) => request.post('/calendar/insert', Data)
export const update = (Data) => request.put('/calendar/update', Data)
export const _delete = (data) => request.delete('/calendar/delete', { params: data })
export const deleteByIds = (data) => request.delete('/calendar/deleteByIds', { params: data })

//file:
export const getFileList = () => request.get('/file/getFileList')
export const deleteFile = (data) => request.delete('/file/deleteFile', { params: data })

//community:
export const getCommunityList = () => request.get('/community/list')
export const communityInsert = (Data) => request.post('/community/insert', Data)

//mail:
export const sendCode = (Data) => request.post('/mail/code', Data)
export const checkCode = (Data) => request.post('/mail/check', Data)
export const resetSend = (Data) => request.post('/mail/resetSend', Data)
export const resetCheck = (Data) => request.post('/mail/resetCheck', Data)
export const resetPassword = (Data) => request.post('/mail/resetPassword', Data)

//normalQuestions:
export const getQuestions = () => request.get('/normalQuestions/getQuestions')

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
