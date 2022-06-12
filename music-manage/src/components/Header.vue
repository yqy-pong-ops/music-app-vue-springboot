<template>
    <div class="header-wrapper">
        <div class="collapse-btn" @click="toggleCollapse">
            <i class="el-icon-menu"></i>
        </div>

        <div class="title">{{title}}</div>

        <div class="right-side">
            <div class="full-screen" @click="handleFS">
                <el-tooltip :content="isFS?'取消全屏':'全屏'" placement="bottom" effect="dark">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <img src="../assets/images/user.jpg" alt class="user-avatar" />
            <el-dropdown @command="handleCmd" class="dd-menu">
                <span class="el-dropdown-link">
                    {{admin.name}}
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item
                        v-for="item in ddOpts"
                        :key="item.cmd"
                        :command="item.cmd"
                    >{{item.title}}</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapState } from "vuex";

export default {
    name: "Header",
    data() {
        return {
            title: "music后台管理",
            isFS: false,
            ddOpts: [
                {
                    title: "个人信息",
                    cmd: "userInfo",
                },
                {
                    title: "退出登录",
                    cmd: "logout",
                },
            ],
        };
    },
    computed: {
        ...mapState(["admin"]),
    },
    methods: {
        toggleCollapse() {
            this.$bus.$emit("toggleCollapse");
        },
        handleFS() {
            if (this.isFS) {
                // 退出全屏
                document.exitFullscreen();
            } else {
                // 全屏
                document.documentElement.requestFullscreen();
            }
            this.isFS = !this.isFS;
        },
        logout() {
            this.$router.push({ name: "Login" });
            localStorage.clear();
            this.$store.commit("setAdmin", null);
        },
        handleCmd(cmd) {
            if (cmd == "logout") this.logout();
            else console.log(cmd);
        },
    },
};
</script>

<style scoped>
.header-wrapper {
    position: relative;
    height: 70px;
    background-color: #253041;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    line-height: 70px;
    height: 70px;
    padding: 0 21px;
    float: left;
    /* margin-left: 30px; */
    cursor: pointer;
}
.collapse-btn:hover {
    color: #20a0ff;
}
.title {
    float: left;
    line-height: 70px;
    font-size: 18px;
}

.right-side {
    float: right;
    margin-right: 50px;
    display: flex;
    align-items: center;
    height: 70px;
}

.right-side .full-screen {
    cursor: pointer;
    transform: rotate(45deg);
}
.right-side .user-avatar {
    height: 40px;
    width: 40px;
    border-radius: 50%;
    margin-left: 10px;
}
.dd-menu {
    margin-left: 10px;
}
.dd-menu .el-dropdown-link {
    cursor: pointer;
    color: #409eff;
}
.dd-menu .el-icon-arrow-down {
    font-size: 12px;
}
</style>