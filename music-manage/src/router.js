import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: () => import('./views/Login.vue')
        },
        {
            path: '/signup',
            name: 'SignUp',
            component: () => import('./views/SignUp.vue')
        }
    ]
})

export default router;