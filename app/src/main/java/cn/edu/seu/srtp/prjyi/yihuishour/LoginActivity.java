/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    Button mRegisterButton;
    EditText mEditUsername;
    EditText mEditPassword;
    View.OnClickListener mLisLogin;
    View.OnClickListener mLisRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.id_button_login);
        mRegisterButton = (Button) findViewById(R.id.id_button_register);
        mEditUsername = (EditText) findViewById(R.id.id_edit_username);
        mEditPassword = (EditText) findViewById(R.id.id_edit_pwd);

        mLisLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mLoginButton.setOnClickListener(mLisLogin);

        mLisRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mRegisterButton.setOnClickListener(mLisRegister);
    }
}
