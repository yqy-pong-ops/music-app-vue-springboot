import Vue from 'vue';
import VueRouter from 'vue-router';
import { Message } from 'element-ui';
import { ADMINTOKEN } from './global';

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/',
            redirect: {
                name: 'Info',
            }
        },
        {
            path: '/home',
            // name: 'Home',
            component: () => import('./views/Home.vue'),
            children: [
                {
                    path: '',
                    name: 'Info',
                    component: () => import('./views/pages/Info.vue')
                },
                {
                    path: 'singer',
                    name: 'Singer',
                    component: () => import('./views/pages/Singer.vue')
                },
                {
                    path: 'sheet',
                    name: 'Sheet',
                    component: () => import('./views/pages/Sheet.vue')
                },
                {
                    path: 'consumer',
                    name: 'Consumer',
                    component: () => import('./views/pages/Consumer.vue')
                },
                {
                    path: 'song',
                    name: 'Song',
                    component: () => import('./views/pages/Song.vue')
                }
            ]
        },
        {
            path: '/login',
            name: 'Login',
            component: () => import('./views/Login.vue')
        },
        {
            path: '/signup',
            name: 'SignUp',
            component: () => import('./views/SignUp.vue')
        },
        {
            path: '/test',
            name: 'Test',
            component: () => import('./views/Test.vue')
        },
        {
            path: '*',
            name: '404',
            component: () => import('./views/404.vue')
        }
    ]
})

// 无需登录的路由
const NoAuthRoutes = ['Login', 'SignUp'];

// beforeEach是个递归调用函数
router.beforeEach((to, from, next) => {
    // to and from are both route objects. must call `next`.
    const res = NoAuthRoutes.indexOf(to.name);
    // console.log('go into route guard');
    if (res != -1)
        return next();
    const token = localStorage.getItem(ADMINTOKEN);
    if (token != null)
        return next()
    Message.error('您尚未登录');
    router.push({ name: 'Login' });
})

export default router;