package com.teamwish.projectvitality.util;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuzalj on 3/28/2015.
 */
public class AsyncNetworkUtil extends AsyncTask<String, Void, Void>{

    private OnNetworkFetchComplete listener;


    public AsyncNetworkUtil(OnNetworkFetchComplete listener){
        this.listener = listener;
    }

    public AsyncNetworkUtil(){

    }

    @Override
    protected Void doInBackground(String... params) {
        if(params[0].equals("getState")){
            String state = getState();
            if(listener != null)
                listener.onComplete("getState", state);
        }else if(params[0].equals("setState")){
            String state = setState();
            if(listener != null)
                listener.onComplete("setState", state);
        }else if(params[0].equals("setCustomState")){
            setCustomState(params[1]);
        }else if(params[0].equals("setLightState")){
            setLightState(params[1]);
        }
        return null;
    }

    private String setLightState(String state){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.SPARK_URL + Constants.SET_LIGHT_STATE);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("access_token", Constants.ACCESS_TOKEN));
        pairs.add(new BasicNameValuePair("params", state));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);

            String sparkResponse = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(sparkResponse);
            Log.d("Vitality", json.toString());
            return Integer.toString(json.getInt("return_value"));

        } catch (UnsupportedEncodingException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (ClientProtocolException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (JSONException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        }
        return "0";
    }

    private String setCustomState(String state){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.SPARK_URL + Constants.SET_CUSTOM_STATE_FUNCTION);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("access_token", Constants.ACCESS_TOKEN));
        pairs.add(new BasicNameValuePair("params", state));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);

            String sparkResponse = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(sparkResponse);

            return Integer.toString(json.getInt("return_value"));

        } catch (UnsupportedEncodingException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (ClientProtocolException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (JSONException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        }
        return "0";
    }

    private String setState(){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.SPARK_URL + Constants.SET_STATE_FUNCTION);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("access_token", Constants.ACCESS_TOKEN));
        pairs.add(new BasicNameValuePair("params", " "));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);

            String sparkResponse = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(sparkResponse);

            return Integer.toString(json.getInt("return_value"));

        } catch (UnsupportedEncodingException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (ClientProtocolException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (JSONException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        }
        return "0";
    }

    private String getState(){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.SPARK_URL + Constants.GET_STATE_FUNCTION);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("access_token", Constants.ACCESS_TOKEN));
        pairs.add(new BasicNameValuePair("params", " "));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);

            String sparkResponse = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(sparkResponse);

            return Integer.toString(json.getInt("return_value"));

        } catch (UnsupportedEncodingException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (ClientProtocolException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        } catch (JSONException e) {
            Log.e("Vitality", Log.getStackTraceString(e));
        }
        return "0";
    }

    public interface OnNetworkFetchComplete{
        void onComplete(String command, String response);
    };
}
