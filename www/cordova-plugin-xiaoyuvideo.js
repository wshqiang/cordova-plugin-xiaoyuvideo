var exec = require('cordova/exec');

var xiaoyuvideo = function(){};

xiaoyuvideo.prototype.callXiaoyuvideo = function(arg0,arg1,arg2,arg3,arg4,arg5,success,error){
    console.log("传入的参数："+[arg0,arg1,arg2,arg3,arg4,arg5]);
    exec(success,error,"XiaoyuVideoPlugin","callXiaoyuvideo",[arg0,arg1,arg2,arg3,arg4,arg5]);
}

var video = new xiaoyuvideo();
module.exports = video;
