/*
 * Created by Pixel Frame on 2017/11/1.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText editUsername;
    EditText editPassword;
    EditText editConfirmPassword;
    View.OnClickListener lisRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = (Button) findViewById(R.id.id_button_reg);
        editUsername = (EditText) findViewById(R.id.id_edit_username);
        editPassword = (EditText) findViewById(R.id.id_edit_pwd);
        editConfirmPassword = (EditText) findViewById(R.id.id_edit_conpwd);

        lisRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                if(username.contains("--")|
                        password.contains("--")|
                        username.matches(".*\\s+.*")) {
                    Toast.makeText(RegisterActivity.this, "用户名或密码非法", Toast.LENGTH_SHORT).show();
                    return;
                }
                register(username, password);
            }
        };
        registerButton.setOnClickListener(lisRegister);
    }

    private void register(final String username, final String password) {
        RequestQueue requestQueue = newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.RegisterURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String status = response.substring(response.lastIndexOf('.')+1 ,response.length());
                        String msg = response.substring(0, response.lastIndexOf('.'));
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                        if(status.equals("0")) finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("register", "true");
                params.put("username", username);
                params.put("password", password);
                params.put("con_password", editConfirmPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
