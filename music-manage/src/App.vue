<!--
 * @Author: Axiuxiu
 * @Date: 2022-05-31 11:33:55
 * @LastEditTime: 2022-06-11 20:08:54
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

export default {
    name: "App",
    components: {},
    methods: {
        refreshVuex() {
            const token = localStorage.getItem(this.$GLOBAL.adminToken);
            if (token == null) return;
            const id = jwt_decode(token).id;
            const name = localStorage.getItem(this.$GLOBAL.adminName);

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
