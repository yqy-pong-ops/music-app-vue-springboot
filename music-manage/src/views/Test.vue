<!--
 * @Author: Axiuxiu
 * @Date: 2022-06-08 20:32:02
 * @LastEditTime: 2022-06-08 20:49:25
 * @Description: 测试视图
 * @Todo: 
-->
<template>
    <div class="test-wrapper">
        <el-col :span="4" :offset="0" class="col col1"></el-col>
        <el-col :span="8" :offset="1" class="col col2"></el-col>
    </div>
</template>

<script>
export default {
    name: "Test",
};
</script>

<style scoped>
.col{
    height: 100px;
}
.col1 {
    background-color: #bfa;
    position: absolute;
    top: 50px;
    bottom: 0;
    height: auto;
    
}
.col2 {
    background-color: violet;
}
</style>
