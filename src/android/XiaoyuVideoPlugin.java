package com.ces.wssspt;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;

import com.ainemo.sdk.NemoSDK;
import com.ainemo.sdk.NemoSDKListener;
import com.ainemo.sdk.model.Settings;
import com.ainemo.shared.call.CallState;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

/**
 * Created by wangshaoqiang on 2017/11/9.
 */

public class XiaoyuVideoPlugin extends CordovaPlugin{

    static final String T = "AndroidSDKSample";

    private NemoSDK nemoSDK; // 小鱼SDK

    private CallbackContext callbackContext;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.i("initialize", "初始化成功");
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws org.json.JSONException{

        this.callbackContext = callbackContext;
        if ("callXiaoyuvideo".equals(action)){
            Log.e("params",args.toString());
            String meetingNumber = args.getString(0);
            boolean requirePassword = args.getBoolean(1);
            String password = args.getString(2);

            String id = args.getString(3);
            String userId = args.getString(4);
            String userName = args.getString(5);
            //初始化视频sdk
            this.nemoSDK = NemoSDK.getInstance();
            Settings settings = new Settings("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", false, false);  // production 环境
            this.nemoSDK.init(cordova.getActivity().getApplicationContext(), settings);

            Log.e("aaaa",meetingNumber+requirePassword+password+id+userId+userName);
            //通过Intent绑定将要调用的Activity
            Intent intent=new Intent(cordova.getActivity(),XiaoyuVideoActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("meetingNumber",meetingNumber);
            bundle.putBoolean("requirePassword",requirePassword);
            bundle.putString("password",password);

            bundle.putString("id",id);
            bundle.putString("userId",userId);
            bundle.putString("userName",userName);

            intent.putExtras(bundle);
            // 启动activity
            cordova.startActivityForResult(XiaoyuVideoPlugin.this,intent, 0);

            // 会议事件通知
            this.nemoSDK.setListener(new NemoSDKListener() {
                @Override
                public void onParticipantChange(int participantsCount) {  // 与会者人数变化事件
                    Log.e(T, "onParticipantChange: current participantsCount:" + participantsCount);
                }

                @Override
                public void onCallStateChange(CallState callState) {  // 呼叫状态改变事件
                    Log.e(T, "onCallStateChange: " + callState.name());
                    if(callState.name().equals("CALL_STATE_DISCONNECTED")){
                        XiaoyuVideoActivity.instance.finish();
                    }
                }
            });
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 根据resultCode判断处理结果
        if (resultCode == Activity.RESULT_OK) {
            this.callbackContext.success("小鱼视频调用成功");
        }
    }
}