package com.ces.wssspt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.ainemo.sdk.NemoSDK;
import com.ainemo.sdk.callback.MakeCallMeetingCallback;

import com.ainemo.sdk.model.Meeting;
import com.ainemo.sdk.model.Result;
import com.ainemo.sdk.model.User;

public class XiaoyuVideoActivity extends Activity {

    static final String T = "AndroidSDKSample";

    static XiaoyuVideoActivity instance;

    private NemoSDK nemoSDK; // 小鱼SDK

    private TextView logTextView;
    private EditText h323NumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        instance  = this;

        nemoSDK = NemoSDK.getInstance();

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        String meetingNumber = bundle.getString("meetingNumber");
        boolean requirePassword = bundle.getBoolean("requirePassword");
        String password = bundle.getString("password");

        String id = bundle.getString("id");
        String userId = bundle.getString("userId");
        String userName = bundle.getString("userName");


        if(requirePassword){
            this.callAnonymousMeeting(meetingNumber,password,userId,userName);
        }else{
            this.callMeeting(meetingNumber,id,userName);
        }
    }

    /**
     * 非登陆模式调用视频
     * @param meetingNumber
     * @param password
     * @param userId
     * @param userName
     */
    private void callAnonymousMeeting(String meetingNumber,String password,String userId,String userName){

        User me = new User();
        me.setDisplayName(userName);
        me.setExternalUserId(userId); // 第三方系统的UserId，保证唯一性

        // 如果不需要回调，callback设置null即可
        nemoSDK.makeCallMeeting(new Meeting(meetingNumber, password), me, new MakeCallMeetingCallback() {
            @Override
            public void onDone(Meeting meeting, Result result) {
                Log.i(T, "makeCallMeeting onDone, meeting: " + meeting + " , result: " + result);
            }
        });

    }

    /**
     * 登陆模式调用视频
     * @param meetingNumber
     * @param id
     * @param userName
     */
    private void callMeeting(String meetingNumber,String id,String userName){
        User me = new User();
        me.setSecurityKey("5df9d17699d30dd4a4498bfadfb23c0c1540331a838");
        me.setCellPhone("+86-960");
        me.setDisplayName(userName);
        me.setId(Long.parseLong(id));
        me.setProfilePicture("1395-a5d8917e-6765-437f-b689-250880016e4b-1442906111412");

        // 如果不需要回调，callback设置null即可
        nemoSDK.makeCallMeeting(new Meeting(meetingNumber, ""), me, new MakeCallMeetingCallback() {
            @Override
            public void onDone(Meeting meeting, Result result) {
                Log.i(T, "makeCallMeeting onDone, meeting: " + meeting + " , result: " + result);
            }
        });
    }
}