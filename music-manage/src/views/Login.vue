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
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="loginForm.name"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="pwd">
                    <el-input v-model="loginForm.pwd" type="password"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                    <el-button @click="jumpTo('SignUp')">注册</el-button>
                    <!-- <el-button @click="codeTest">注册</el-button> -->
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import { getLoginStatus } from "@/api";
import { ADMINNAME, ADMINTOKEN, DATA, TOKEN } from '@/global';

export default {
    name: "Login",
    data() {
        return {
            labelPosition: "left",
            loginForm: {
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
                    // 发送表单数据
                    getLoginStatus(this.loginForm)
                        .then((data) => {
                            const token = data[TOKEN];
                            localStorage.setItem(
                                ADMINTOKEN,
                                token
                            );
                            const name = data[DATA].name;
                            localStorage.setItem(ADMINNAME, name);

                            const id = jwt_decode(token).id;

                            // 写入vuex
                            this.$store.commit("setAdmin", {
                                name,
                                id,
                            });
                            // 跳转页面
                            let next = this.$route.query.next;
                            if (next) this.$router.replace(next);
                            else this.$router.push({ name: "Info" });
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
    padding: 20px 25px 10px 20px;
    background-color: #fff;
    border-radius: 5px;
    width: 330px;
}
</style>