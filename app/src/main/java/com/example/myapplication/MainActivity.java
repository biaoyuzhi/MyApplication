package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    static {
        //该动态库名称来自CMakeList.txt文件的add_library()处声明
        System.loadLibrary("wuzh");
    }
    private EditText et_wav;
    private EditText et_mp3;
    private ProgressDialog pd;

    //定义2个native方法，通过JNI调用C语言实现，并且给后面代码调用
    public native void convertmp3(String wav, String mp3);
    public native String getLameVersion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_wav = (EditText) this.findViewById(R.id.et_wav);
        et_mp3 = (EditText) this.findViewById(R.id.et_mp3);
        pd = new ProgressDialog(this);
    }

    /**
     * wav转换mp3
     */
    public void convert(View view) {
        final String mp3Path = et_mp3.getText().toString().trim();
        final String wavPath = et_wav.getText().toString().trim();
        File file = new File(wavPath);
        int size = (int) file.length();
        System.out.println("文件大小 " + size);
        if ("".equals(mp3Path) || "".equals(wavPath)) {
            Toast.makeText(MainActivity.this, "路径不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        pd.setMessage("转换中....");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(size); // 设置进度条的最大值
        pd.setCancelable(false);
        pd.show();
        // 转码是个耗时的操作，所以这里需要开启新线程去执行
        new Thread() {

            @Override
            public void run() {
                convertmp3(wavPath, mp3Path);
                pd.dismiss();
            }

        }.start();
    }

    /**
     * 设置进度条的进度，提供给C语言调用
     *
     * @param progress
     */
    public void setConvertProgress(int progress) {
        pd.setProgress(progress);
    }

    /**
     * 获取LAME的版本号
     */
    public void getVersion(View view) {
        Toast.makeText(MainActivity.this, getLameVersion(), Toast.LENGTH_SHORT).show();
    }
}
