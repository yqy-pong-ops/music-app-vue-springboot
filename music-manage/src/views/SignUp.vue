<template>
    <div class="signup-wrapper">
        <div class="ms-title">music 后台管理系统</div>

        <div class="form-wrapper">
            <el-form
                :label-position="labelPosition"
                :model="signupForm"
                ref="signupForm"
                :rules="rules"
                label-width="70px"
                :inline="false"
                size="normal"
            >
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="signupForm.name"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="pwd">
                    <el-input v-model="signupForm.pwd" type="password"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('signupForm')">注册</el-button>
                    <el-button @click="jumpTo('Login')">去登录</el-button>
                    <!-- <el-button @click="codeTest">注册</el-button> -->
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { signUp } from "@/api";

export default {
    name: "SignUp",
    data() {
        return {
            labelPosition: "left",
            signupForm: {
                name: "",
                pwd: "",
            },
            rules: {
                name: [
                    {
                        required: true,
                        message: "请输入用户名",
                        trigger: "blur",
                    },
                ],
                pwd: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                    {
                        min: 6,
                        max: 20,
                        message: "密码长度必须在6-20之间",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 注册
                    signUp(this.signupForm)
                        .then(() => {
                            return;
                        })
                        .catch((err) => {
                            console.log(err);
                        });
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
.signup-wrapper {
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
    padding: 20px 25px 10px 20px;
    background-color: #fff;
    border-radius: 5px;
    width: 330px;
}
</style>