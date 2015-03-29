package com.teamwish.projectvitality.util;

/**
 * Created by kuzalj on 3/28/2015.
 */
public class NetworkUtil {

    public void getState(AsyncNetworkUtil.OnNetworkFetchComplete listener) {
        new AsyncNetworkUtil(listener).execute("getState");
    }

    public void setLightState(String state) {
        new AsyncNetworkUtil().execute("setLightState", state);
    }

    public void setCustomState(String state) {
        new AsyncNetworkUtil().execute("setCustomState", state);
    }

    public void setState(AsyncNetworkUtil.OnNetworkFetchComplete listener) {
        new AsyncNetworkUtil(listener).execute("setState");
    }

    public void setState() {
        new AsyncNetworkUtil().execute("setState");
    }


}
