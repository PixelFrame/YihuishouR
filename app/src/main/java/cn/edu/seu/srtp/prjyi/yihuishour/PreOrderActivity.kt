/*
 * Created by Pixel Frame on 2017/11/4.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import cn.edu.seu.srtp.prjyi.yihuishour.util.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import java.text.SimpleDateFormat
import java.util.*



class PreOrderActivity : AppCompatActivity() {

    private var ItemCata : Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_order)
        val RBtnGrp : RadioGroup = findViewById(R.id.id_rbgroup_cata)
        val RBtn0 : RadioButton = findViewById(R.id.id_rbtn_0)
        val RBtn1 : RadioButton = findViewById(R.id.id_rbtn_1)
        val RBtn2 : RadioButton = findViewById(R.id.id_rbtn_2)
        val RBtn3 : RadioButton = findViewById(R.id.id_rbtn_3)
        val RBtn4 : RadioButton = findViewById(R.id.id_rbtn_4)
        val RBtn5 : RadioButton = findViewById(R.id.id_rbtn_5)
        val ConBtn : Button = findViewById(R.id.id_button_pre_confirm)
        RBtnGrp.setOnCheckedChangeListener { rGroup, _ ->
            ItemCata = when (rGroup.checkedRadioButtonId) {
                RBtn0.id -> 0
                RBtn1.id -> 1
                RBtn2.id -> 2
                RBtn3.id -> 3
                RBtn4.id -> 4
                RBtn5.id -> 5
                else -> 0
            }
        }
        val mLisCon = View.OnClickListener {
            sendOrder()
        }
        ConBtn.setOnClickListener(mLisCon)
    }

    private fun sendOrder() {
        val requestQueue = newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST, _CONSTANTS.OrderURL,
                Response.Listener {
                    response -> Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    finish()
                },
                Response.ErrorListener {
                    Toast.makeText(this, "发生错误", Toast.LENGTH_LONG).show()
                }) {
            override fun getParams(): Map<String, String> {
                val globalData = application as GlobalData
                val params = HashMap<String, String>()
                params.put("uid", Integer.toString(globalData.user.id))
                val orders = ArrayList<Order>()
                orders.add(createOrder())
                try {
                    params.put("scanRes", "PreOrder")
                    params.put("xmlStr", XmlParser.serialize_order(orders))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun createOrder(): Order {
        val EtNum : EditText = findViewById(R.id.id_edit_num)
        val EtLoc : EditText = findViewById(R.id.id_edit_location)
        val item = Item()
        item.num = Integer.valueOf(EtNum.text.toString())
        item.catagory = ItemCata
        item.name = "用户预约物品"
        item.id = -100000
        val order = Order()
        order.newItem(item)
        order.status = 0
        order.alias = "预约订单"
        order.attrib = 1
        order.date = Integer.parseInt(SimpleDateFormat("yyMMddhhmm", Locale.CHINESE).format(Date())).toLong()
        order.location = EtLoc.toString()
        return order
    }
}