/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 17:30:13
 * @LastEditTime: 2022-06-16 14:33:38
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