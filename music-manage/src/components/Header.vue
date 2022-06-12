<template>
    <div class="header-wrapper">
        <div class="collapse-btn" @click="toggleCollapse">
            <i class="el-icon-menu"></i>
        </div>
    </div>
</template>

<script>
export default {
    name: "Header",
    methods: {
        toggleCollapse() {
            this.$bus.$emit("toggleCollapse");
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
</style>