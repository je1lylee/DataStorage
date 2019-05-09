package top.linxixiangxin.datastorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Permission;

public class file_persist extends AppCompatActivity {
    private EditText editText;
    private Button saveToFile, SaveToSD, readData, readSD;
    private TextView result;
    private static final String TAG = "dialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_persist);
        editText = findViewById(R.id.editText);
        saveToFile = findViewById(R.id.button);
        SaveToSD = findViewById(R.id.button_save_sdcard);
        readData = findViewById(R.id.button2);
        readSD = findViewById(R.id.button_read_sdcard);
        result = findViewById(R.id.textView);
        SaveToSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >=23){//SDK>=23需要动态申请权限
                    if(checkCallingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},300);
                    }else{
                        saveToSD("saveToSD",editText.getText().toString());
                    }

                }else{
                    saveToSD("saveToSD",editText.getText().toString());
                }
            }
        });
        saveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                saveDataToInner(content);

            }
        });
        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = readFromInner();
                result.setText(content);

            }
        });
    }
    private void saveToSD(String fileName, String FileContent){
        //保存到SD卡
        //TODO 判断SD卡状态是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()){ //证实SD卡可用
            Log.d(TAG, "saveToSD: SD卡可用");
            try{
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File file = new File(path,fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(FileContent.getBytes());
                fos.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
        }
    }

    private String readFromInner() {
        FileInputStream FileIn = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileIn = openFileInput("innerData");
            reader = new BufferedReader(new InputStreamReader(FileIn));
            String s = "";
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    private void saveDataToInner(String content) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("innerData", MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
            Toast.makeText(this, "数据保存成功！", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {//文件找不到
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
