/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 17:30:13
 * @LastEditTime: 2022-06-12 15:44:43
 * @Description: 网络接口
 * @Todo: 
 */
import { post } from './http';

export const getLoginStatus = (data) => post('/manage/admin/login', data);
export const signUp = (data) => post('/manage/admin/signup', data); 
