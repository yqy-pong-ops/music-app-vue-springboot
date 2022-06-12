<template>
    <div class="home-wrapper">
        <Header></Header>
        <Aside></Aside>
        <!-- 页面 -->
        <router-view></router-view>
    </div>
</template>

<script>
import Aside from "../components/Aside.vue";
import Header from "../components/Header.vue";

export default {
    name: "Home",
    components: { Aside, Header },
};
</script>

<style scoped>

</style>