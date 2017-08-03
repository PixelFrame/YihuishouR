/*
 * Created by Pixel Frame on 2017/8/3.
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
                register();
            }
        };
        registerButton.setOnClickListener(lisRegister);
    }

    private void register() {
        RequestQueue requestQueue = newRequestQueue(this);
        String loginURL = "http://115.159.188.117/PHP/register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
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
                params.put("register","true");
                params.put("username",editUsername.getText().toString().trim());
                params.put("password",editPassword.getText().toString().trim());
                params.put("con_password",editConfirmPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
