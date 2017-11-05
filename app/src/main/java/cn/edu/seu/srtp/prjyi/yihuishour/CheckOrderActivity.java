/*
 * Created by Pixel Frame on 2017/11/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.Order;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.android.volley.toolbox.Volley.newRequestQueue;

public class CheckOrderActivity extends AppCompatActivity {

    TextView tvOid;
    TextView tvItem;
    TextView tvDate;
    TextView tvLocation;
    Button btnCancel;
    Button btnEditLoca;
    View.OnClickListener lisCancel;
    View.OnClickListener lisEditLoca;
    DialogInterface.OnClickListener lisConCancel;
    DialogInterface.OnClickListener lisConEditLoca;
    RequestQueue requestQueue;
    Order order;
    int oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        tvOid = findViewById(R.id.id_tv_co_oid);
        tvDate = findViewById(R.id.id_tv_co_date);
        tvItem = findViewById(R.id.id_tv_co_item);
        tvLocation = findViewById(R.id.id_tv_co_location);
        btnCancel = findViewById(R.id.id_button_cancel);
        btnEditLoca = findViewById(R.id.id_button_co_edit);
        requestQueue = newRequestQueue(this);
        init();
    }
    private void init() {
        Intent it = getIntent();
        oid = it.getIntExtra("oid", -1);
        getOrder();
        tvOid.setText(String.valueOf(oid));
        final EditText newLoca = new EditText(this);

        lisConCancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == BUTTON_POSITIVE) sendCancel();
                else dialog.dismiss();
            }
        };
        lisConEditLoca = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == BUTTON_POSITIVE) sendEditLoca(newLoca.getText().toString());
                else dialog.dismiss();
            }
        };

        final AlertDialog CancelDialog = new AlertDialog.Builder(this)
                .setTitle("确认")
                .setMessage("确认取消订单？")
                .setPositiveButton("是", lisConCancel)
                .setNegativeButton("否", lisConCancel)
                .create();
        lisCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        CancelDialog.show();
            }
        };

        final AlertDialog EditDialog = new AlertDialog.Builder(this)
                .setTitle("请输入")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(newLoca)
                .setPositiveButton("确定", lisConEditLoca)
                .setNegativeButton("取消", lisConEditLoca)
                .create();
        lisEditLoca = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        EditDialog.show();
            }
        };
    }

    private void getOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.GetOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("NULL")) {
                            Toast.makeText(CheckOrderActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            try {
                                order = XmlParser.parse_order(response).get(0);
                                updateView();
                                btnCancel.setOnClickListener(lisCancel);
                                btnEditLoca.setOnClickListener(lisEditLoca);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(CheckOrderActivity.this, "获取订单时发送系统错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("oid", String.valueOf(oid));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateView() {
        String ItemCat = null;
        if (order.getItems().isEmpty()) tvItem.setText("无");
        else {
            switch (order.getItems().get(0).getCatagory()) {
                case 0:
                    ItemCat = getString(R.string.Type_0);
                    break;
                case 1:
                    ItemCat = getString(R.string.Type_1);
                    break;
                case 2:
                    ItemCat = getString(R.string.Type_2);
                    break;
                case 3:
                    ItemCat = getString(R.string.Type_3);
                    break;
                case 4:
                    ItemCat = getString(R.string.Type_4);
                    break;
                case 5:
                    ItemCat = getString(R.string.Type_5);
                    break;
            }
            tvItem.setText(String.format("物品类别: %s\n物品数量: %s",
                    ItemCat,
                    order.getItems().get(0).getNum()));
        }
        tvDate.setText(order.dateToString());
        if(order.getLocation() == null) {
            tvLocation.setText("无");
        } else {
            tvLocation.setText(order.getLocation());
        }
    }

    private void sendCancel() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.EditOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("成功")) {
                            Toast.makeText(CheckOrderActivity.this, "已取消", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CheckOrderActivity.this, "取消失败，请重试", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("type", "0");
                params.put("oid", String.valueOf(oid));
                params.put("status", "-1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void sendEditLoca(final String newLoca) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.EditOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("成功")) {
                            Toast.makeText(CheckOrderActivity.this, "修改成功，刷新订单中", Toast.LENGTH_LONG).show();
                            getOrder();
                        } else {
                            Toast.makeText(CheckOrderActivity.this, "修改失败，请重试", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("type", "1");
                params.put("oid", String.valueOf(oid));
                params.put("location", newLoca);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
