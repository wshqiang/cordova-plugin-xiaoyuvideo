#cordova-plugin-xiaoyuvideo

本插件用于调用小鱼视频功能，目前只适用于Android平台

#安装方式
cordova plugin add cordova-plugin-xiaoyuvideo --variable AINEMO_APP_ID=小鱼视频授权AINEMO_APP_ID

其中用到的AINEMO_APP_ID与应用的package name和证书SHA1签名相关,
获取到本app的packageName和SHA1签名后，提交给小鱼企业后台申请AINEMO_APP_ID

#使用方法

1、声明变量：declare var XiaoyuVideoPlugin : any;

2、调用小鱼视频的官方接口获取会议室列表，接口会返回 “会议号”、“会议密码”和“是否需要密码进入”等信息

3、利用步骤2返回的信息调用小鱼视频，调用方法：
    XiaoyuVideoPlugin.callXiaoyuvideo(${会议号},${是否需要密码进入},${会议密码},${登录人id},${登录人id},${登录人姓名}).then(status,message=>{
        console.log(status);
        console.log(message);
    }).catch(status,message=>{
        console.log(status);
        console.log(message);
    });

