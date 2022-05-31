<template>
    <div class="login-wrapper">
        <div class="ms-title">music 后台管理系统</div>

        <div class="form-wrapper">
            <el-form
                :label-position="labelPosition"
                :model="loginForm"
                ref="loginForm"
                :rules="rules"
                label-width="70px"
                :inline="false"
                size="normal"
            >
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="pwd">
                    <el-input v-model="loginForm.pwd"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                    <el-button @click="$jumpTo('SignUp')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            labelPosition: "left",
            loginForm: {
                username: "",
                pwd: "",
            },
            rules: {
                username: [
                    {
                        required: true,
                        message: "请输入用户名",
                        trigger: "blur",
                    },
                ],
                pwd: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                    { min: 6, max: 20, message: "密码长度必须在6-20之间", trigger: "blur" },
                ],
            },
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {

                    // 向后台发送数据
                    console.log(this.loginForm);
                } else {
                    console.log("form error");
                    return false;
                }
            });
        },
    },
};
</script>

<style scoped>
.login-wrapper {
    background-image: url("../assets/images/background.jpg");
    background-attachment: fixed;
    background-position: center;
    background-size: cover;
    width: 100%;
    height: 100%;
    overflow: hidden;
}
.ms-title {
    margin-top: 50px;
    text-align: center;
    font-size: 30px;
    font-weight: 600;
    color: #fff;
}

.form-wrapper {
    margin: 0 auto;
    margin-top: 30px;
    padding: 20px 20px 10px 20px;
    background-color: #fff;
    border-radius: 5px;
    width: 350px;
}
</style>