/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 17:30:13
 * @LastEditTime: 2022-06-18 16:54:31
 * @Description: 网络接口
 * @Todo: 
 */
import { get, post } from './http';

// admin接口
export const getLoginStatus = (data) => post('/manage/admin/login', data);
export const signUp = (data) => post('/manage/admin/signup', data);

// singer接口
export const getAllSinger = () => get('/manage/singer/getAll');
export const addSinger = (data) => post('/manage/singer/addSinger', data);
export const updateSinger = (data) => post('/manage/singer/updateSinger', data);
export const deleteSinger = (data) => post('/manage/singer/deleteById', data);
export const delBatchSingers = (data) => post('/manage/singer/deleteBatchById', data)

// song接口
// data必须是FormData数据
export const addSong = (data) => post('/manage/song/addSong', data, {
    headers: {
        'Content-Type': 'multipart/form-data',
    }
})
export const getSongsBySingerId = (singerId) => get('/manage/song/getSongsBySingerId', { singerId, });