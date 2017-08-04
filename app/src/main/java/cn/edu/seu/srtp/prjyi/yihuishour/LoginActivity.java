/*
 * Created by Pixel Frame on 2017/8/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button registerButton;
    EditText editUsername;
    EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.id_button_login);
        registerButton = (Button) findViewById(R.id.id_button_logon);
        editUsername = (EditText) findViewById(R.id.id_edit_username);
        editPassword = (EditText) findViewById(R.id.id_edit_pwd);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private void login() {
        RequestQueue requestQueue = newRequestQueue(this);
        String loginURL = "http://115.159.188.117/PHP/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("登录失败")) {
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                GlobalData globalData = (GlobalData) getApplicationContext();
                                globalData.setUser(XmlParser.parse_user(response));
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "系统错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("login", "true");
                params.put("username",editUsername.getText().toString().trim());
                params.put("password",editPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_button_login){
            login();
        } else if(v.getId() == R.id.id_button_logon){
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
