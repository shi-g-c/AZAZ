<script setup>
import { RouterLink, RouterView } from 'vue-router';
import notice from './components/notice.vue';
import axios from 'axios';
import qs from 'qs';
import { h, onMounted, ref } from "vue";
import { NSkeleton, NThing, NBackTop, NAvatar, NNotificationProvider, NMessageProvider, NModal, NCard, NTabs, NForm, NFormItem, NTabPane, NLayout, NLayoutHeader, NLayoutSider, NDivider, NMenu, NConfigProvider, NPopover, NIcon, NButton, NInput } from "naive-ui";

const showLogin = ref(false);
const Userinfo = ref({
  UserImg: null,
  UserName: null,
  UserSignature: null,
});
const Userno = ref({
  UserToken: null,
  UserId: null,
});
const hasLogin = ref(false);
const videoPlayer = ref(false);

axios.defaults.baseURL = 'http://10.134.49.88:8080';    //API接口地址
axios.defaults.timeout = 5000;                         //请求超时时间
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';//请求头
axios.defaults.headers['Accept'] = '*/*';
axios.defaults.headers['Host'] = '10.134.49.88:8080';
axios.defaults.headers['Connection'] = 'keep-alive';

// 定义注册函数
async function register(phone, password)
{
  try
  {
    // 发送请求
    const registerurl = '/azaz/user/login/register?' + qs.stringify({
      phone: phone,
      password: password,
    });
    const response = await axios.post(registerurl);

    // 处理响应
    const responseData = response.data;
    if (responseData.code === 2)
    {
      // 成功注册
      window.$message.success(
        '注册成功，正在自动登录'
      );
      login(phone, password);
    } else
    {
      window.$message.error(
        responseData.message
      );
    }
  } catch (error)
  {
    // 网络请求失败
    window.$message.error(
      '网络异常，请稍后重试。'
    );
  }
}

// 定义登录函数
async function login(phone, password)
{
  try
  {
    // 发送登录请求
    const loginurl = '/azaz/user/login?' + qs.stringify({
      phone: phone,
      password: password,
    });
    const response = await axios.post(loginurl);

    // 处理响应
    const responseData = response.data;
    if (responseData.code === 2)
    {
      // 登录成功
      Userno.value.UserToken = responseData.data.token;
      Userno.value.UserId = responseData.data.userId;
      localStorage.setItem("Useraccount", JSON.stringify({
        UserToken: Userno.value.UserToken,
        UserId: Userno.value.UserId,
      }));
      axios.defaults.headers.common['token'] = Userno.value.UserToken;//登陆后更新token
      window.$message.success(
        '登录成功'
      );
      hasLogin.value = true;
      showLogin.value = false;
      getPersonalInfo(null);
    } else
    {
      window.$message.error(
        responseData.message
      );
    }
  } catch (error)
  {
    // 网络请求失败
    window.$message.error(
      '网络异常，请稍后重试。'
    );
  }
}

//请求用户信息，为空则是查自己
async function getPersonalInfo(Id)
{
  try
  {
    // 发送GET请求
    const getPersonalInfourl = '/azaz/user/personal?' + qs.stringify({
      userId: Id,
    });
    const response = await axios.get(getPersonalInfourl);

    // 检查响应中的数据
    const responseData = response.data;
    if (responseData.code === 2 && Id === null)
    {
      // 从响应数据中提取用户信息
      Userinfo.value.UserName = responseData.data.username;
      Userinfo.value.UserSignature = responseData.data.signature;
      Userinfo.value.UserImg = responseData.data.image;

      // 使用 localStorage 存储数据
      localStorage.setItem("Userdata", JSON.stringify({
        UserName: Userinfo.value.UserName,
        UserSignature: Userinfo.value.UserSignature,
        UserImg: Userinfo.value.UserImg,
      }));
      return responseData.data;
    } else if (responseData.code === 2 && Id !== null)
    {
      return responseData.data;
    } else
    {
      // 未成功获取信息，报错
      window.$message.error(
        response.data.message
      );
      return null;
    }
  } catch (error)
  {
    // 请求失败，报错
    window.$message.error(
      '网络异常，与服务器失联。'
    );
    return null;
  }
}

function checkLogin()
{
  const account = localStorage.getItem('Useraccount');
  if (account)
  {
    const userAccount = JSON.parse(account);
    axios.defaults.headers.common['token'] = userAccount.UserToken;//登陆后更新token
    Userno.value.UserToken = userAccount.UserToken;
    Userno.value.UserId = userAccount.UserId;
    getPersonalInfo(null);
    const userData = JSON.parse(localStorage.getItem("Userdata"));
    Userinfo.value.UserImg = userData.UserImg;
    Userinfo.value.UserName = userData.UserName;
    Userinfo.value.UserSignature = userData.UserSignature;
    hasLogin.value = true;
  } else
  {
    showLogin.value = true;
  }
}

const getQuery = (url, query) =>
{
  // str为？之后的参数部分字符串
  const str = url.substr(url.indexOf('?') + 1);
  // arr每个元素都是完整的参数键值
  const arr = str.split('&');
  // result为存储参数键值的集合
  const result = {};
  for (let i = 0; i < arr.length; i++)
  {
    // item的两个元素分别为参数名和参数值
    const item = arr[i].split('=');
    result[item[0]] = item[1];
  }
  return result[query];
};

onMounted(() =>
{
  const Vid = getQuery(window.location.href, 'Vid');
  // 判断是否包含名为"Vid"的参数
  if (Vid)
  {

    videoPlayer.value = true;
  } else
  {
  }
  checkLogin();
});

const loginRef = ref(null);
const registerRef = ref(null);
const rPasswordFormItemRef = ref(null);
const loginmodel = ref({
  number: null,
  password: null
});
const registermodel = ref({
  number: null,
  password: null,
  repassword: null
});
function validatePasswordStartWith(rule, value)
{
  return !!registermodel.value.password && registermodel.value.password.startsWith(value) && registermodel.value.password.length >= value.length;
}
function validatePasswordSame(rule, value)
{
  return value === registermodel.value.password;
}
function handlePasswordInput()
{
  if (registerRef.value.repassword)
  {
    rPasswordFormItemRef.value?.validate({ trigger: "password-input" });
  }
}
function registerButtonClick(e)
{
  e.preventDefault();
  registerRef.value?.validate((errors) =>
  {
    if (!errors)
    {
      register(registermodel.value.number, registermodel.value.password);
    } else
    {
      window.$message.error(
        '请先检查填写的内容'
      );
    }
  });
}
function loginButtonClick(e)
{
  e.preventDefault();
  loginRef.value?.validate((errors) =>
  {
    if (!errors)
    {
      login(loginmodel.value.number, loginmodel.value.password);
    } else
    {
      window.$message.error(
        '请先检查填写的内容'
      );
    }
  });
}
const loginrules = {
  number: [
    {
      required: true,
      validator(rule, value)
      {
        if (!value)
        {
          return new Error("请输入电话号码");
        } else if (!/^\d*$/.test(value))
        {
          return new Error("电话号码应该只含数字");
        } else if (value.length != 11)
        {
          return new Error("电话号码应为11位");
        }
        return true;
      },
      trigger: ["input", "blur"]
    }
  ],
  password: [
    {
      required: true,
      validator(rule, value)
      {
        if (!value)
        {
          return new Error("请输入密码");
        } else if (value.length < 6)
        {
          return new Error("密码应该至少有6位");
        } else if (value.length > 20)
        {
          return new Error("密码应该不超过20位");
          //} else if (!/^(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).+$/.test(value))
          //{
          //  return new Error("密码应该同时包含数字、字母和特殊字符");
        } else if (!/^[a-zA-Z0-9!@#$%,.?]+$/.test(value))
        {
          return new Error("密码不应含有非法字符");
        }
        return true;
      },
      trigger: ["input", "blur"]
    }
  ]
};
const morerules = {
  repassword: [
    {
      required: true,
      message: "请再次输入密码",
      trigger: ["input", "blur"]
    },
    {
      validator: validatePasswordStartWith,
      message: "两次密码输入不一致",
      trigger: "input"
    },
    {
      validator: validatePasswordSame,
      message: "两次密码输入不一致",
      trigger: ["blur", "password-input"]
    }
  ]
};
const registerrules = { ...morerules, ...loginrules };

//接受子组件传递来的消息并触发行为
function wanted(message)
{
  if (message === 'checkLogin')
  {
    checkLogin();
  }
}
</script>

<template>
  <n-config-provider :theme-overrides="themeOverrides">
    <n-message-provider><n-notification-provider>
        <notice />
      </n-notification-provider></n-message-provider>
    <div style="position: relative">
      <n-modal v-model:show="showLogin" style="max-width: 350px;" :mask-closable="false">
        <n-card title="欢迎来到AZAZ">
          <n-tabs default-value="signin" size="large" justify-content="space-evenly">
            <n-tab-pane name="signin" tab="登录">
              <n-form ref="loginRef" :model="loginmodel" :rules="loginrules">
                <n-form-item label="手机号" path="number">
                  <n-input placeholder="请输入手机号码" v-model:value="loginmodel.number" @keydown.enter.prevent />
                </n-form-item>
                <n-form-item label="密码" path="password">
                  <n-input v-model:value="loginmodel.password" type="password" show-password-on="mousedown"
                    placeholder="请输入密码" />
                </n-form-item>
              </n-form>
              <n-button type="primary" block secondary strong
                :disabled="loginmodel.number === null || loginmodel.password === null" @click="loginButtonClick">
                登录
              </n-button>
            </n-tab-pane>
            <n-tab-pane name="signup" tab="注册">
              <n-form ref="registerRef" :model="registermodel" :rules="registerrules">
                <n-form-item label="手机号" path="number">
                  <n-input placeholder="请输入手机号码" v-model:value="registermodel.number" @keydown.enter.prevent />
                </n-form-item>
                <n-form-item label="密码" path="password">
                  <n-input v-model:value="registermodel.password" @input="handlePasswordInput" @keydown.enter.prevent
                    type="password" show-password-on="mousedown" placeholder="请输入密码" />
                </n-form-item>
                <n-form-item ref="rPasswordFormItemRef" first path="repassword" label="确认密码">
                  <n-input v-model:value="registermodel.repassword" :disabled="!registermodel.password" type="password"
                    show-password-on="mousedown" placeholder="请再次输入密码" />
                </n-form-item>
              </n-form>
              <n-button type="primary" block secondary strong
                :disabled="registermodel.number === null || registermodel.password === null" @click="registerButtonClick">
                注册
              </n-button>
            </n-tab-pane>
          </n-tabs>
        </n-card>
      </n-modal>
      <n-layout position="absolute" style="height: 100vh;">
        <n-layout-header style="width: 100vw;height: 64px; border-bottom: 2px solid #dbdbdb;">
          <div class="title-container">
            <div class="title-left"><span id="web-title">AZAZ</span></div>
            <div class="title-center">
              <n-input round clearable placeholder="搜索" style="max-width: 500px;">
                <!-- TODO:搜索路由拼接 -->
                <template #prefix>
                  <n-icon>
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                      viewBox="0 0 32 32">
                      <g fill="none">
                        <path
                          d="M13.5 3C7.701 3 3 7.701 3 13.5S7.701 24 13.5 24c2.54 0 4.868-.901 6.684-2.402l7.109 7.11a1 1 0 0 0 1.414-1.415l-7.109-7.11A10.457 10.457 0 0 0 24 13.5C24 7.701 19.299 3 13.5 3zM5 13.5a8.5 8.5 0 1 1 17 0a8.5 8.5 0 0 1-17 0z"
                          fill="currentColor"></path>
                      </g>
                    </svg>
                  </n-icon>
                </template>
              </n-input>
            </div>
            <div class="title-right">
              <div class="title-box" style="height: 29px;">
                <RouterLink to="/upload">
                  <n-popover trigger="hover">
                    <template #trigger>
                      <n-button text>
                        <n-icon size="29">
                          <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                            viewBox="0 0 1024 1024">
                            <path
                              d="M400 317.7h73.9V656c0 4.4 3.6 8 8 8h60c4.4 0 8-3.6 8-8V317.7H624c6.7 0 10.4-7.7 6.3-12.9L518.3 163a8 8 0 0 0-12.6 0l-112 141.7c-4.1 5.3-.4 13 6.3 13zM878 626h-60c-4.4 0-8 3.6-8 8v154H214V634c0-4.4-3.6-8-8-8h-60c-4.4 0-8 3.6-8 8v198c0 17.7 14.3 32 32 32h684c17.7 0 32-14.3 32-32V634c0-4.4-3.6-8-8-8z"
                              fill="currentColor"></path>
                          </svg>
                        </n-icon>
                      </n-button>
                    </template>
                    <span>上传视频</span>
                  </n-popover>
                </RouterLink>
              </div>
              <div class="title-box" style="height: 29px;">
                <RouterLink to="/messages">
                  <n-popover trigger="hover">
                    <template #trigger>
                      <n-button text>
                        <n-icon size="29">
                          <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                            viewBox="0 0 1024 1024">
                            <path
                              d="M464 512a48 48 0 1 0 96 0a48 48 0 1 0-96 0zm200 0a48 48 0 1 0 96 0a48 48 0 1 0-96 0zm-400 0a48 48 0 1 0 96 0a48 48 0 1 0-96 0zm661.2-173.6c-22.6-53.7-55-101.9-96.3-143.3a444.35 444.35 0 0 0-143.3-96.3C630.6 75.7 572.2 64 512 64h-2c-60.6.3-119.3 12.3-174.5 35.9a445.35 445.35 0 0 0-142 96.5c-40.9 41.3-73 89.3-95.2 142.8c-23 55.4-34.6 114.3-34.3 174.9A449.4 449.4 0 0 0 112 714v152a46 46 0 0 0 46 46h152.1A449.4 449.4 0 0 0 510 960h2.1c59.9 0 118-11.6 172.7-34.3a444.48 444.48 0 0 0 142.8-95.2c41.3-40.9 73.8-88.7 96.5-142c23.6-55.2 35.6-113.9 35.9-174.5c.3-60.9-11.5-120-34.8-175.6zm-151.1 438C704 845.8 611 884 512 884h-1.7c-60.3-.3-120.2-15.3-173.1-43.5l-8.4-4.5H188V695.2l-4.5-8.4C155.3 633.9 140.3 574 140 513.7c-.4-99.7 37.7-193.3 107.6-263.8c69.8-70.5 163.1-109.5 262.8-109.9h1.7c50 0 98.5 9.7 144.2 28.9c44.6 18.7 84.6 45.6 119 80c34.3 34.3 61.3 74.4 80 119c19.4 46.2 29.1 95.2 28.9 145.8c-.6 99.6-39.7 192.9-110.1 262.7z"
                              fill="currentColor"></path>
                          </svg>
                        </n-icon>
                      </n-button>
                    </template>
                    <span>聊天消息</span>
                  </n-popover>
                </RouterLink>
              </div>
              <div class="title-box title-user">
                <n-button v-if="!hasLogin" strong secondary type="primary" @click="{ showLogin = true; }">
                  登录/注册
                </n-button>
                <RouterLink :to="'/user/' + Userno.UserId" class="title-user"><n-avatar v-if="hasLogin" round :size="35"
                    :src="Userinfo.UserImg" /></RouterLink>
              </div>
            </div>
          </div>
        </n-layout-header>
        <n-layout has-sider position="absolute" style="top: 64px">
          <n-layout-sider content-style="padding-top: 20px">
            <n-menu :options="menuOptions" />
            <n-divider />
            <p style="margin-left: 20px;color: #8a8b9f;">Designed by AZAZ Team</p>
          </n-layout-sider>
          <n-layout content-style="padding: 24px;">
            <RouterView v-slot:="{ Component, route }"><n-back-top />
              <component @wanted="wanted" :is="Component" :key="route.path" />
            </RouterView>
          </n-layout>
        </n-layout>
      </n-layout>

    </div>
    <div style="position: relative" id="video-canva" v-if="videoPlayer">
      <div id="video-player-container">
        <div id="video-player-close">
          <n-button circle size="large" color="#505050" @click="videoPlayer = false">
            <n-icon size="30"><svg color="white" xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 512 512" style="">
                <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"
                  d="M368 368L144 144" style=""></path>
                <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"
                  d="M368 144L144 368" style=""></path>
              </svg></n-icon>
          </n-button>
        </div>
        <div id="video-player-frame">
          <video id="video-player-video"
            src="https://webstatic.mihoyo.com/upload/op-public/2023/01/13/ff5f7fe208a947bf0c7c0239c54411f4_592291034584067074.mp4"
            controls="controls" autoplay="autoplay" preload=""></video>
        </div>
      </div>
      <div id="video-detail-container">
        <div>
          <n-thing>
            <template #avatar>
              <n-avatar round :size="10" :src="Userinfo.UserImg" style="margin-right: 20px;" />
            </template>
            <template #header>
              <p id="user-name">{{ Userinfo.UserName }}</p>
            </template>
            <template #header-extra>
              <n-button circle size="large" @click="share">
                <template #icon>
                  <n-icon size="25">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                      viewBox="0 0 24 24">
                      <g fill="none">
                        <path
                          d="M6.747 4h3.464a.75.75 0 0 1 .102 1.493l-.102.007H6.747a2.25 2.25 0 0 0-2.245 2.095l-.005.155v9.5a2.25 2.25 0 0 0 2.096 2.244l.154.006h9.5a2.25 2.25 0 0 0 2.245-2.096l.005-.154v-.498a.75.75 0 0 1 1.494-.102l.006.102v.498a3.75 3.75 0 0 1-3.55 3.744l-.2.006h-9.5a3.75 3.75 0 0 1-3.745-3.551l-.005-.2v-9.5a3.75 3.75 0 0 1 3.55-3.744l.2-.005h3.464h-3.464zM14.5 6.52V3.75a.75.75 0 0 1 1.187-.61l.082.068l5.994 5.75c.28.269.306.7.077.998l-.077.085l-5.994 5.752a.75.75 0 0 1-1.262-.435l-.007-.107v-2.725l-.344.03c-2.4.25-4.7 1.331-6.914 3.26c-.52.453-1.323.025-1.237-.658c.664-5.32 3.446-8.251 8.195-8.619l.3-.02V3.75v2.77zM16 5.507V7.25a.75.75 0 0 1-.75.75c-3.874 0-6.274 1.676-7.312 5.157l-.079.278l.352-.237C10.45 11.737 12.798 11 15.251 11a.75.75 0 0 1 .743.649l.007.101v1.743L20.16 9.5l-4.16-3.992z"
                          fill="currentColor"></path>
                      </g>
                    </svg>
                  </n-icon>
                </template>
              </n-button>
            </template>

            <n-skeleton text :repeat="0" size="small" style="width: 20%;" />
          </n-thing>
          <div id="video-detail-flow">
            <n-tabs type="line" animated size="large"
              tab-style="font-size: 18px;letter-spacing: 2px;margin: 0 10px 0 10px;font-weight: bold;">
              <n-tab-pane name="publish" tab="评论">
                <n-skeleton height="400px" width="100%" />
              </n-tab-pane>
            </n-tabs>
          </div>
        </div>
      </div>
    </div>
  </n-config-provider>
</template>

<script>
const themeOverrides = {
  common: {
    primaryColor: '#0750ff',
    primaryColorHover: '#0783ff',
    primaryColorPressed: '#0438b2',
    primaryColorSuppl: '#0783ff'
  },
  Menu: {
    itemHeight: '42px'
  },
};
const menuOptions = [
  {
    label: () => h(
      RouterLink,
      {
        to: {
          path: "/"
        }
      },
      { default: () => h('h2', { class: 'menu-item-h' }, "主页") }
    ),
    key: "home",
    icon: () => h('svg', {
      xmlns: 'http://www.w3.org/2000/svg',
      viewBox: '0 0 32 32',
    }, [
      h('path', {
        d: 'M16.612 2.214a1.01 1.01 0 0 0-1.242 0L1 13.419l1.243 1.572L4 13.621V26a2.004 2.004 0 0 0 2 2h20a2.004 2.004 0 0 0 2-2V13.63L29.757 15L31 13.428zM18 26h-4v-8h4zm2 0v-8a2.002 2.002 0 0 0-2-2h-4a2.002 2.002 0 0 0-2 2v8H6V12.062l10-7.79l10 7.8V26z',
        fill: 'currentColor',
      })
    ])
  },
  {
    label: () => h(
      RouterLink,
      {
        to: {
          path: "/following"
        }
      },
      { default: () => h('h2', { class: 'menu-item-h' }, "关注") }
    ),
    key: "following",
    icon: () => h('svg', {
      xmlns: 'http://www.w3.org/2000/svg',
      viewBox: '0 0 32 32',
    }, [
      h('path', {
        d: 'M32 14h-4v-4h-2v4h-4v2h4v4h2v-4h4v-2z',
        fill: 'currentColor',
      }),
      h('path', {
        d: 'M12 4a5 5 0 1 1-5 5a5 5 0 0 1 5-5m0-2a7 7 0 1 0 7 7a7 7 0 0 0-7-7z',
        fill: 'currentColor',
      }),
      h('path', {
        d: 'M22 30h-2v-5a5 5 0 0 0-5-5H9a5 5 0 0 0-5 5v5H2v-5a7 7 0 0 1 7-7h6a7 7 0 0 1 7 7z',
        fill: 'currentColor',
      })
    ])
  },
  {
    label: () => h(
      RouterLink,
      {
        to: {
          path: "/explore"
        }
      },
      { default: () => h('h2', { class: 'menu-item-h' }, "探索") }
    ),
    key: "explore",
    icon: () => h('svg', {
      xmlns: 'http://www.w3.org/2000/svg',
      viewBox: '2 0 32 32',
    }, [
      h('path', {
        d: 'M22.707 9.293a1 1 0 0 0-1.023-.242l-9 3a1.001 1.001 0 0 0-.633.633l-3 9a1 1 0 0 0 1.265 1.265l9-3a1.001 1.001 0 0 0 .633-.633l3-9a1 1 0 0 0-.242-1.023zM11.581 20.42l2.21-6.628l4.419 4.419z',
        fill: 'currentColor',
      }),
      h('path', {
        d: 'M16 30a14 14 0 1 1 14-14a14.016 14.016 0 0 1-14 14zm0-26a12 12 0 1 0 12 12A12.014 12.014 0 0 0 16 4z',
        fill: 'currentColor',
      })
    ])
  },
  {
    label: () => h(
      RouterLink,
      {
        to: {
          path: "/live"
        }
      },
      { default: () => h('h2', { class: 'menu-item-h' }, "直播") }
    ),
    key: "live",
    icon: () => h('svg', {
      xmlns: 'http://www.w3.org/2000/svg',
      viewBox: '1 -2 26 32',
    }, [
      h('path', {
        d: 'M9 10v8l7-4zm12-4h-7.58l3.29-3.29L16 2l-4 4h-.03l-4-4l-.69.71L10.56 6H3c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm0 14H3V8h18v12z',
        fill: 'currentColor',
      })
    ])
  }
];
</script>

<style>
#video-player-video {
  width: 100%;
  height: 100%;
}

#video-player-close {
  position: absolute;
  top: 20px;
  left: 20px;
}

#video-player-container {
  width: 68vw;
  height: 100%;
  background-color: black;
  display: flex;
  align-items: center;
}

#video-detail-container {
  width: 32vw;
  height: 100%;
  background-color: white;
}

#video-canva {
  height: 100vh;
  width: 100vw;
  position: fixed;
  top: 0px;
  left: 0px;
  z-index: 30;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

#web-title {
  font-size: 26px;
  font-weight: bold;
}

.title-user {
  display: flex;
  align-items: center;
}

.title-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.title-left {
  flex: 0 0 auto;
  margin-left: 20px;
  width: 220px;
}

.title-center {
  flex: 1 0 auto;
  display: flex;
  justify-content: center;
}

.title-right {
  flex: 0 0 auto;
  margin-right: 20px;
  width: 220px;
  text-align: right;
  display: flex;
  justify-content: right;
  align-items: center;
}

.title-box {
  display: inline-block;
  margin-left: 20px;
}

.menu-item-h {
  padding-left: 10px;
  font-size: 19px;
}
</style>