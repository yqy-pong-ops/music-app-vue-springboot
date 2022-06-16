<!--
 * @Author: Axiuxiu
 * @Date: 2022-05-31 11:33:55
 * @LastEditTime: 2022-06-16 10:57:29
 * @Description: 
 * @Todo: 
-->
<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import { ADMINNAME, ADMINTOKEN } from './global';

export default {
    name: "App",
    components: {},
    methods: {
        refreshVuex() {
            const token = localStorage.getItem(ADMINTOKEN);
            if (token == null) return;
            const id = jwt_decode(token).id;
            const name = localStorage.getItem(ADMINNAME);

            this.$store.commit("setAdmin", {
                name,
                id,
            });
        },
    },
    mounted() {
        this.refreshVuex();
    },
};
</script>

<style>
</style>
