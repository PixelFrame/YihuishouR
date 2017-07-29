/*
 * Created by Pixel Frame on 2017/7/29.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

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

/**
 * Created by pm421 on 7/29/2017.
 */

public class XmlRequest extends Request<XmlPullParser> {
    private final Response.Listener<XmlPullParser> mListener;

    public XmlRequest(int method, String url, Response.Listener<XmlPullParser> listener,
                      Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }
    /*parseNetworkResponse()方法中则应该对服务器响应的数据进行解析，
    其中数据是以字节的形式存放在NetworkResponse的data变量中的，
    这里将数据取出然后组装成一个String，并传入Response的success()方法中即可。*/
    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));
            return Response.success(parser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }

    }
    /*deliverResponse()方法中的实现很简单，仅仅是调用了mListener中的onResponse()方法，
    并将response内容传入即可，这样就可以将服务器响应的数据进行回调了。*/
    @Override
    protected void deliverResponse(XmlPullParser xmlPullParser) {
        mListener.onResponse(xmlPullParser);
    }
}
