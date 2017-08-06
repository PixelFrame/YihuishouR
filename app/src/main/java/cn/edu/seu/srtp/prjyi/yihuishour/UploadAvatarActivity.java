/*
 * Created by Pixel Frame on 2017/8/6.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;

public class UploadAvatarActivity extends AppCompatActivity implements View.OnClickListener{
    private String capturePath = null;

    Button btnUpload;
    Button btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_avatar);
        btnUpload = (Button) findViewById(R.id.id_button_upload);
        btnCapture = (Button) findViewById(R.id.id_button_capture);
        btnUpload.setOnClickListener(this);
        btnCapture.setOnClickListener(this);
    }

    protected void getImageFromAlbum() {  
       Intent intent = new Intent(Intent.ACTION_PICK);
       intent.setType("image/*");//相片类型  
       startActivityForResult(intent, 1);
    }

    protected void getImageFromCamera() {  
        String state = Environment.getExternalStorageState();  
        if (state.equals(Environment.MEDIA_MOUNTED)) {  
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");  
            String out_file_path = "YihuishouCapture/";
            File dir = new File(out_file_path);  
            if (!dir.exists()) {  
                dir.mkdirs();  
            }  
            capturePath = "YihuishouCapture/capture_" + System.currentTimeMillis() + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);  
            startActivityForResult(getImageByCamera, 2);
        }
        else {  
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();  
        }  
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Uri uri = data.getData();
            final String img_url = getRealFilePath(this, uri);
            new Thread(){
                @Override
                public void run(){
                    doUpload(img_url);
                }
            }.start();
        } else if (requestCode == 2 ) {
            Uri uri = data.getData();
            if (uri == null) {
                final String img_url = capturePath;
                new Thread(){
                    @Override
                    public void run(){
                        doUpload(img_url);
                    }
                }.start();
            } else {
                final String img_url = getRealFilePath(this, uri);
                new Thread(){
                    @Override
                    public void run(){
                        doUpload(img_url);
                    }
                }.start();
            }
        }
    }

    public void doUpload(String pathString)
    {
        String ext = "jpg";
        if ((pathString != null) && (pathString.length() > 0)) {
            int dot = pathString.lastIndexOf('.');
            if ((dot >-1) && (dot < (pathString.length() - 1))) {
                ext = pathString.substring(dot + 1);
            }
        }
        GlobalData globalData = (GlobalData) getApplicationContext();
        String acceptUrl = "http://115.159.188.117/PHP/edit_avatar.php?fid="+globalData.getUser().getId()+"&ext="+ext;

        RandomAccessFile raf =  null;
        try
        {
            raf = new RandomAccessFile(pathString, "r");
            long alllength = raf.length();
            raf.seek(0);
            byte[] buffer = new byte[128*1024];//128k
            int count = 0;
            while ((count = raf.read(buffer)) != -1)
            {
                String result = uploadFil(acceptUrl,buffer);
                System.out.println("MediaActivity doUpload return:"+result+ " count:"+count);
                break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(raf!=null)
                    raf.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String uploadFil(String acceptUrl, byte[] data)
    {


        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try
        {
            URL url = new URL(acceptUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.setChunkedStreamingMode(data.length);// 128*1024 是128k

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);//application/octet-stream   multipart/form-data
            DataOutputStream dos  = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; pathString=\""
                    +"uploaded"
                    +"\""
                    +end);
            dos.writeBytes(end);


            dos.write(data,0,data.length);

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();


            String reponse = "";
            if(httpURLConnection.getResponseCode() == 200 )
            {
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);
                while (null !=br.readLine())
                {
                    reponse +=br.readLine();

                }
                is.close();
            }

            System.out.println("MediaActivity uploadFil Reponse:"+reponse);
            dos.close();
            return reponse;

        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("MediaActivity uploadFil Exception:"+e.getMessage());
        }

        return "";

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.id_button_capture){
            getImageFromCamera();
        } else {
            getImageFromAlbum();
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}