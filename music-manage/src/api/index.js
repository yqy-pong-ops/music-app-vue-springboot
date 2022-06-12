/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 17:30:13
 * @LastEditTime: 2022-06-11 22:34:41
 * @Description: 网络接口
 * @Todo: 
 */
import { post } from './http';

export const getLoginStatus = (data) => post('/manage/admin/login', data);
