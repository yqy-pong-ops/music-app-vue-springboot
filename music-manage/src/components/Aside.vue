<template>
    <div class="aside-wrapper">
        <el-menu
            class="sidebar-menu"
            mode="vertical"
            :collapse="collapse"
            :default-active="onRoute"
            @select="handleSelect"
            background-color="#334256"
            text-color="#fff"
            active-text-color="#20a0ff"
        >
            <el-menu-item v-for="(item, index) in menuItems" :key="index" :index="item.index">
                <i :class="item.icon"></i>
                <span class="title">{{item.title}}</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
export default {
    name: "Aside",
    data() {
        return {
            collapse: false,
            menuItems: [
                {
                    icon: "el-icon-document",
                    title: "系统首页",
                    index: "Info",
                },
                {
                    icon: "el-icon-document",
                    title: "用户管理",
                    index: "Consumer",
                },
                {
                    icon: "el-icon-document",
                    title: "歌手管理",
                    index: "Singer",
                },
                {
                    icon: "el-icon-document",
                    title: "歌单管理",
                    index: "Sheet",
                },
            ],
        };
    },
    methods: {
        handleSelect(index) {
            this.jumpTo(index);
        },
    },
    computed: {
        onRoute() {
            return this.$route.name;
        },
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
.aside-wrapper {
    position: absolute;
    top: 70px;
    bottom: 0;
    left: 0;
    background-color: #334256;
    overflow: auto;
}
.aside-wrapper::-webkit-scrollbar {
    width: 0;
}

.sidebar-menu {
    height: 100%;
}

.sidebar-menu.el-menu--collapse .el-menu-item .title {
    display: none;
}

.sidebar-menu:not(.el-menu--collapse) {
    width: 150px;
}
</style>