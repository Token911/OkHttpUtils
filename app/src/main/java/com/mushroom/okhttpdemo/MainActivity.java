package com.mushroom.okhttpdemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.GoogleApiClient;
import com.gz.okhttp.OkHttpUtil;
import com.gz.okhttp.listener.DisposeDataHandler;
import com.gz.okhttp.listener.DisposeDataListener;
import com.gz.okhttp.listener.DisposeFileListener;
import com.gz.okhttp.response.CommonFileCallback;
import com.gz.okhttp.response.CommonJsonCallback;

import java.io.File;
import java.io.FileNotFoundException;

import okhttp3.OkHttpClient;


public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TYPE = "application/octet-stream";
    private EditText et_show_text;
    private Button btn_request_get;
    private Button btn_request_post;
    private Button btn_request_postparams;
    private Button btn_request_upload;
    private Button btn_request_download;


    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
    public static String SAVED_IMAGE_DIR_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + "/AppName/camera/";// 拍照路径
    String cameraPath;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;
    private ImageView iv_compress;
    private OkHttpClient client;
    private ProgressBar pb_download;
    private File file2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        File externalStorageDirectory = Environment.getExternalStorageDirectory();

        file2 = new File("/mnt/shared/Image","123.jpg");
    }

    

    

    private void initViews() {
        iv_compress = (ImageView) findViewById(R.id.iv_compress);
        et_show_text = (EditText) findViewById(R.id.et_showresposetext);
        btn_request_get = (Button) findViewById(R.id.btn_request_get);
        btn_request_post = (Button) findViewById(R.id.btn_request_post);
        btn_request_postparams = (Button) findViewById(R.id.btn_request_post_params);
        btn_request_upload = (Button) findViewById(R.id.btn_request_upload);
        btn_request_download = (Button) findViewById(R.id.btn_request_down);
        pb_download = (ProgressBar) findViewById(R.id.pb_download);
    }

    private void initListener() {
      btn_request_download.setOnClickListener(this);
      btn_request_get.setOnClickListener(this);
      btn_request_post.setOnClickListener(this);
      btn_request_postparams.setOnClickListener(this);
      btn_request_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_get:
                requestServerGetMethod();
                break;
            case R.id.btn_request_post:

                break;
            case R.id.btn_request_post_params:
                requestServerPostMethod();
                break;
            case R.id.btn_request_upload:
                uploadFile();
                break;
            case R.id.btn_request_down:
                et_show_text.setText("我被执行了啊");
                downloadFile();
                break;
            default:
                break;
        }
    }


    /**Get请求服务器*/
    private void requestServerGetMethod() {
        OkHttpUtil.get().url("http://192.168.56.1:8080/LoginDemo/servlet/LoginServlet")
                .execute(new CommonJsonCallback(new DisposeDataHandler(new DisposeDataListener<User>() {
            @Override
            public void onDisposeFailure(Exception e) {

            }

            @Override
            public void onDisposeSuccess(User user) {
                if(user != null) {
                    Log.e("TAG", user.toString());
                }
            }
        },User.class)));
    }
    /**上传文件*/
    private void uploadFile() {
        try {
            OkHttpUtil.upload().url("http://192.168.1.4:8080/LoginDemo/servlet/FileUpLoad").addParams("file.jpg",file2)
                    .execute(new CommonJsonCallback(new DisposeDataHandler(new DisposeDataListener<User>() {
                        @Override
                        public void onDisposeFailure(Exception e) {
                            Log.e("TAG","上传成功");
                        }
                        @Override
                        public void onDisposeSuccess(User user) {
                            if(user != null) {
                                Log.e("TAG","上传失败");
                            }
                        }
                    })));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**下载文件*/
    private void downloadFile() {
        OkHttpUtil.download().url("http://www.xiaopi.com/down-1-11777-1.html")
                .execute(new CommonFileCallback(new DisposeDataHandler(new DisposeFileListener() {
            @Override
            public void downloadFileBefore() {
                Log.e("TAG","准备下载");
            }

            @Override
            public void downloadFileProgress(int progress, long totalProgress) {
                int currentProgress = (int) ((progress*100)/totalProgress);
                Log.e("TAG","当前进度: "+currentProgress + "总进度: " + totalProgress);
            }

            @Override
            public void onDisposeFailure(Exception e) {
                Log.e("TAG","下载失败");
            }
            @Override
            public void onDisposeSuccess(Object o) {
                Log.e("TAG","下载成功");
            }
        },file2.toString())));
    }

    /**Post方式向服务器请求*/
    private void requestServerPostMethod() {
        OkHttpUtil.post().url("http://192.168.56.1:8080/LoginDemo/servlet/LoginServlet").addParams("name","123").addParams("psw","123")
                .execute(new CommonJsonCallback(new DisposeDataHandler(new DisposeDataListener<User>() {
                    @Override
                    public void onDisposeFailure(Exception e) {

                        Log.e("TAG","123");
                    }

                    @Override
                    public void onDisposeSuccess(User o) {
                        Log.e("TAG",o.toString());
                    }
                },User.class)));
    }



}
