<template>
    <div class="home-wrapper">
        <Header></Header>
        <Aside></Aside>
        <!-- 页面 -->
        <div class="content-box" :class="{'content-collapse':collapse}">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import Aside from "../components/Aside.vue";
import Header from "../components/Header.vue";

export default {
    name: "Home",
    components: { Aside, Header },
    data() {
        return {
            collapse: false,
        };
    },
    mounted() {
        this.$bus.$on("toggleCollapse", () => {
            this.collapse = !this.collapse;
        });
    },
    beforeDestroy() {
        this.$bus.$off("toggleCollapse");
    },
};
</script>

<style scoped>
.content-box {
    position: absolute;
    top: 70px;
    left: 150px;
    bottom: 0;
    right: 0;
    overflow: auto;
    transition: left 0.3s ease-in-out;
    background-color: #f0f0f0;
}
.content-collapse {
    left: 65px;
}
</style>