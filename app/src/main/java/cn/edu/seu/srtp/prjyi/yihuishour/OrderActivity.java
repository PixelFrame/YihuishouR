/*
 * Created by Pixel Frame on 2017/7/28.
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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Button mScan = (Button) findViewById(R.id.id_button_scan);
        View.OnClickListener mLisScan = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customScan();
            }
        };
        mScan.setOnClickListener(mLisScan);

    }

    public void customScan(){
        new IntentIntegrator(this)
                .setPrompt("请将二维码置于框内")
                .setOrientationLocked(false)
                .setCaptureActivity(ScanActivity.class)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,"扫描成功"+ScanResult,Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}
