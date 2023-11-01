<script setup>
import { ref, onMounted, defineEmits, defineProps } from 'vue';
import { NInput, NListItem, NList, NModal, NResult, NSkeleton, NTabPane, NTabs, NNotificationProvider, NMessageProvider, NAvatar, NThing, NButton, NIcon } from 'naive-ui';
import axios from 'axios';
import FlowCard from '../components/videocard.vue';
import notice from '../components/notice.vue';
import qs from 'qs';

const props = defineProps(['id']);
const emit = defineEmits(['wanted']);

axios.defaults.baseURL = 'http://10.134.49.88:8080';    //API接口地址
axios.defaults.timeout = 5000;                         //请求超时时间
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';//请求头
axios.defaults.headers['Accept'] = '*/*';
axios.defaults.headers['Host'] = '10.134.49.88:8080';
axios.defaults.headers['Connection'] = 'keep-alive';

const editInfo = ref(false);
const Userinfo = ref({
    UserImg: null,
    UserName: null,
    UserSignature: null,
});
const Editinfo = ref({
    UserImg: null,
    UserName: null,
    UserSignature: null,
});
const Userno = ref({
    UserToken: null,
    UserId: null,
});

// 定义修改信息函数
async function editinfo()
{
    try 
    {
        // 发送请求
        const editurl = '/azaz/user/personal?' + qs.stringify({
            image: Editinfo.value.UserImg,
            username: Editinfo.value.UserName,
            signature: Editinfo.value.UserSignature,
        });
        const response = await axios.put(editurl);

        // 处理响应
        const responseData = response.data;
        if (responseData.code === 2)
        {
            // 成功
            window.$message.success(
                '信息修改成功'
            );
            Userinfo.value.UserImg = Editinfo.value.UserImg;
            Userinfo.value.UserName = Editinfo.value.UserName;
            Userinfo.value.UserSignature = Editinfo.value.UserSignature;
            localStorage.setItem("Userdata", JSON.stringify({
                UserName: Userinfo.value.UserName,
                UserSignature: Userinfo.value.UserSignature,
                UserImg: Userinfo.value.UserImg,
            }));
            Editinfo.value = false;
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
            '信息修改失败，网络异常，请稍后重试。'
        );
    }
}

function share()
{
    const currentURL = window.location.href.split('?')[0];
    if (navigator.clipboard)
    {
        navigator.clipboard.writeText(currentURL).then(() =>
        {
            window.$message.success(
                '成功复制分享链接'
            );
        }).catch(err =>
        {
            window.$message.error(
                '复制出错，请手动复制页面链接进行分享'
            );
        });
    } else
    {
        // 处理不支持Clipboard API 的情况
        window.$message.error(
            '当前浏览器不支持自动复制，请手动复制页面链接进行分享'
        );
    }
}

function exit()
{
    localStorage.removeItem('Useraccount');
    localStorage.removeItem('Userdata');
    window.$message.success(
        '成功退出登录'
    );
    setTimeout(() => { window.location.href = '/'; }, 2000);
}

onMounted(() =>
{
    emit('wanted', 'checkLogin');      //应当保证登录
    const userAccount = JSON.parse(localStorage.getItem('Useraccount'));
    axios.defaults.headers.common['token'] = userAccount.UserToken;//登陆后更新token
    Userno.value.UserToken = userAccount.UserToken;
    Userno.value.UserId = userAccount.UserId;
    const userData = JSON.parse(localStorage.getItem("Userdata"));
    Userinfo.value.UserImg = userData.UserImg;
    Userinfo.value.UserName = userData.UserName;
    Userinfo.value.UserSignature = userData.UserSignature;
    Editinfo.value.UserImg = userData.UserImg;
    Editinfo.value.UserName = userData.UserName;
    Editinfo.value.UserSignature = userData.UserSignature;
});
</script>

<template>
    <n-message-provider><n-notification-provider>
            <notice />
        </n-notification-provider></n-message-provider>
    <n-modal v-model:show="editInfo" preset="card" style="max-width: 500px;" id="edit-info" title="修改个人信息" size="huge"
        :bordered="false">

        <n-list hoverable clickable>
            <n-list-item>
                <n-thing content-style="margin-top: 10px;" content-indented id="edit-avatar">
                    <template #avatar>
                        <span class="edit-info-title">头像</span>
                    </template>
                    <template #description>
                        <n-avatar round :size="100" :src="Userinfo.UserImg" style="margin-right: 20px;" />
                    </template>
                    <template #footer>
                        点击头像即可上传新头像。
                    </template>
                </n-thing>
            </n-list-item>
            <n-list-item>
                <n-thing content-style="margin-top: 10px;" content-indented>
                    <template #avatar>
                        <span class="edit-info-title">昵称</span>
                    </template>
                    <template #description>
                        <n-input v-model:value="Editinfo.UserName" type="text" :placeholder="Userinfo.UserName" show-count
                            :maxlength="20" :count-graphemes="countGraphemes" />
                    </template>
                    <template #footer>
                        昵称只能包含字母、数字、下划线和句点。不超过20个字符。
                    </template>
                </n-thing>
            </n-list-item>
            <n-list-item>
                <n-thing content-style="margin-top: 10px;" content-indented>
                    <template #avatar>
                        <span class="edit-info-title">个性签名</span>
                    </template>
                    <template #description>
                        <n-input v-model:value="Editinfo.UserSignature" type="textarea"
                            :placeholder="Userinfo.UserSignature" show-count :maxlength="100"
                            :count-graphemes="countGraphemes" />
                    </template>
                    <template #footer>
                        请输入个性签名，不超过100个字符。
                    </template>
                </n-thing>
            </n-list-item>
        </n-list>
        <div class="edit-btn"><n-button strong secondary @click="editinfo" class="edit-button-sub">
                <n-icon size="20">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32">
                        <path
                            d="M27.307 6.107L30 3.414L28.586 2l-2.693 2.693L24.8 3.6a1.933 1.933 0 0 0-2.8 0l-18 18V28h6.4l18-18a1.933 1.933 0 0 0 0-2.8zM9.6 26H6v-3.6L23.4 5L27 8.6z"
                            fill="currentColor"></path>
                        <path d="M9 11.586L16.586 4L18 5.414L10.414 13z" fill="currentColor"></path>
                    </svg>
                </n-icon>
                <p class="edit-p">提交</p>
            </n-button></div>
    </n-modal>
    <div>
        <n-thing>
            <template #avatar>
                <n-avatar round :size="150" :src="Userinfo.UserImg" style="margin-right: 20px;" />
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
            <template #description class="edit-container">
                <n-button @click="editInfo = true" class="edit-button">
                    <n-icon size="20">
                        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                            viewBox="0 0 32 32">
                            <path
                                d="M27.307 6.107L30 3.414L28.586 2l-2.693 2.693L24.8 3.6a1.933 1.933 0 0 0-2.8 0l-18 18V28h6.4l18-18a1.933 1.933 0 0 0 0-2.8zM9.6 26H6v-3.6L23.4 5L27 8.6z"
                                fill="currentColor"></path>
                            <path d="M9 11.586L16.586 4L18 5.414L10.414 13z" fill="currentColor"></path>
                        </svg>
                    </n-icon>
                    <p class="edit-p">修改个人信息</p>
                </n-button>
                <n-button @click="exit" class="edit-button">
                    <n-icon size="20">
                        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                            viewBox="0 0 32 32">
                            <path d="M26 4h2v24h-2z" fill="currentColor"></path>
                            <path d="M11.414 20.586L7.828 17H22v-2H7.828l3.586-3.586L10 10l-6 6l6 6l1.414-1.414z"
                                fill="currentColor"></path>
                        </svg>
                    </n-icon>
                    <p class="edit-p">安全退出登录</p>
                </n-button>
            </template>
            <n-skeleton text :repeat="0" size="small" style="width: 20%;" />
            <template #footer>
                <p id="user-sig">{{ Userinfo.UserSignature }}</p>
            </template>
        </n-thing>
        <div id="user-flow">
            <n-tabs type="line" animated size="large"
                tab-style="font-size: 18px;letter-spacing: 2px;margin: 0 10px 0 10px;font-weight: bold;">
                <n-tab-pane name="publish" tab="发布">
                    <div class="flow-grid">
                        <FlowCard
                            coverurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" playnumber="281k"
                            title="中国网络游戏新格局，来了！国家新闻出版署定调！" upname="Kevin" likenumber="2346"
                            imgurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D" />
                        <FlowCard
                            coverurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D"
                            playnumber="231k" title="【原神】五个小知识" upname="4444444444444444444444444444444444444444"
                            likenumber="2346" imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://f1-seo.v3mh.com/social/b38d9ed55411249d97324738d83d7d67-watermark.seo"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard
                            coverurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard
                            coverurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard
                            coverurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D"
                            playnumber="231k" title="买周边一定要看清尺寸！一个憨憨，在收到等了半年的预售后傻掉了" upname="Kevin Yu" likenumber="2346"
                            imgurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" />
                        <FlowCard coverurl="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg" playnumber="281k"
                            title="中国网络游戏新格局，来了！国家新闻出版署定调！" upname="Kevin" likenumber="2346"
                            imgurl="https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oAIbQABcRkT2AsnkLejkbpONKHUEIDegQ65dBc~tplv-photomode-zoomcover:480:480.jpeg?x-expires=1698750000&amp;x-signature=JZw9HiIEaXOGsEWyWWql%2F9xff7U%3D" />
                    </div>
                </n-tab-pane>
                <n-tab-pane name="star" tab="收藏">
                    <n-result title="暂无内容" description="去别处看看吧" size="huge">
                        <template #icon>
                            <n-icon size="80" style="margin-top: 40px;"><svg xmlns="http://www.w3.org/2000/svg"
                                    xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32">
                                    <path d="M9 10.555L10.555 9L23 21.444L21.444 23z" fill="currentColor"></path>
                                    <path
                                        d="M16 2A13.914 13.914 0 0 0 2 16a13.914 13.914 0 0 0 14 14a13.914 13.914 0 0 0 14-14A13.914 13.914 0 0 0 16 2zm0 26a12 12 0 1 1 12-12a12.035 12.035 0 0 1-12 12z"
                                        fill="currentColor"></path>
                                </svg></n-icon>
                        </template>
                    </n-result>
                </n-tab-pane>
                <n-tab-pane name="like" tab="已赞">
                    <n-skeleton height="400px" width="100%" />
                </n-tab-pane>
            </n-tabs>
        </div>
    </div>
</template>

<style>
.edit-button-sub {
    height: 40px;
    width: 170px;
}

.edit-btn {
    margin-top: 5px;
    display: flex;
    justify-content: right;
}

#edit-avatar .n-thing-main .n-thing-main__description {
    display: flex;
    justify-content: center;
}

#edit-avatar .n-thing-main .n-thing-main__footer {
    display: flex;
    justify-content: center;
}

#edit-info .n-card-header .n-card-header__main {
    font-size: 30px;
    font-weight: bold !important;
}

.edit-info-title {
    font-size: 20px;
    font-weight: bold;
    display: block;
    width: 120px;
    top: -2px;
    position: relative;
}

.flow-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
    grid-gap: 20px;
    margin: 0 10px 0 10px;
}

#user-flow {
    margin-top: 13px;
}

#user-sig {
    font-size: 15px;
    margin: 5px 0 0 0;
}

.n-thing-header {
    display: flex;
    align-items: flex-start !important;
}

#user-name {
    font-size: 40px;
    font-weight: bold;
    margin: 30px 0 10px 0;
}

.edit-container {
    display: flex;
    justify-content: flex-start;
}

.edit-p {
    margin-left: 8px;
    font-size: 17px;
    font-weight: bold;
}

.edit-button {
    height: 40px;
    width: 170px;
    margin-right: 20px;
}
</style>