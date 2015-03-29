package com.teamwish.projectvitality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.teamwish.projectvitality.util.AsyncNetworkUtil;
import com.teamwish.projectvitality.util.NetworkUtil;
import com.teamwish.projectvitality.util.PowerService;

public class MainActivity extends Activity implements AsyncNetworkUtil.OnNetworkFetchComplete, View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent serviceIntent = new Intent(this, PowerService.class);
        startService(serviceIntent);

        findViewById(R.id.charging_button).setOnClickListener(this);
        Log.d("Vitality", Integer.toString(getSharedPreferences("settings", MODE_PRIVATE).getInt("charge", 100)));
        ((EditText) findViewById(R.id.stop_charge_edit_text)).setText(Integer.toString(getSharedPreferences("settings", MODE_PRIVATE).getInt("charge", 100)));
        ((EditText) findViewById(R.id.stop_charge_edit_text)).addTextChangedListener(this);
       // ((CheckBox) findViewById(R.id.power_off_checkbox)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onComplete(String command, final String response) {
        if(command.equals("setState")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.charging_button).setEnabled(true);
                    int text = response.equals("1") ? R.string.stop_charging : R.string.start_charging;
                    String color = response.equals("1") ? "red" : "off";
                    ((TextView)findViewById(R.id.charging_button)).setText(text);
                    new NetworkUtil().setLightState(color);
                }
            });
        }else if(command.equals("getState")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.charging_button).setEnabled(true);
                    int text = response.equals("1") ? R.string.stop_charging : R.string.start_charging;
                    ((TextView)findViewById(R.id.charging_button)).setText(text);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        findViewById(R.id.charging_button).setEnabled(false);
        new NetworkUtil().getState(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.charging_button){
            v.setEnabled(false);
            new NetworkUtil().setState(this);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("poweroff", isChecked);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().isEmpty())
            getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("charge", Integer.parseInt(s.toString())).commit();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
