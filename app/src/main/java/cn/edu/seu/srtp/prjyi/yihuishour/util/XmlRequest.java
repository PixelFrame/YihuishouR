/*
 * Created by Pixel Frame on 2017/8/26.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.util.Xml;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;

/**
 * Created by pm421 on 7/29/2017.
 */

public class XmlRequest extends Request<XmlPullParser> {
    //Listener接口
    private final Listener<XmlPullParser> mListener;
    //构造方法，其中参数我们应该都很清楚了
    public XmlRequest(int method, String url, Listener<XmlPullParser> listener,
                      ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
    //默认为post请求方式的构造方法
    public XmlRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
    }
    //parseNetworkResponse方法
    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            //第一步加工，将得到的数据转为一个字符串
            String xmlString = new String(response.data, "UTF-8");
            //这些固定代码就写在这里，以便于我们在使用XmlRequest的时候，只需要关注我们如何去解析获得的xml数据就可以了
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlString));
            //第二步加工。直接返回这个xmlPullPaser
            return Response.success(parser, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch(UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }
        catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }
        //deliverResponse方法，直接固定一句话
        @Override
        protected void deliverResponse(XmlPullParser response) {
            mListener.onResponse(response);
        }

    }