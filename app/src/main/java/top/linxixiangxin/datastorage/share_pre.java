package top.linxixiangxin.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class share_pre extends AppCompatActivity {
    private static final String TAG = "dialog";
private Button savedata,restoredata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_pre);
        savedata = findViewById(R.id.save_data);
        restoredata = findViewById(R.id.restore_data);
        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor =getSharedPreferences("dataTest", Context.MODE_PRIVATE).edit();
                editor.putString("Name","Apple");
                editor.putInt("age",200);
                editor.putBoolean("young",true);
                editor.commit();//或者是apply()方法来提交数据更改 commit主线程同步提交 apply交给操作系统 异步处理
                Log.d(TAG, "onClick: SUCCESS ADD DATA");
            }
        });
        restoredata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sps = getSharedPreferences("dataTest",MODE_PRIVATE);
                Log.d(TAG,"name: "+sps.getString("Name",""));
                Log.d(TAG, "age: "+sps.getInt("age",0));
                Log.d(TAG, "yong: "+sps.getBoolean("young",false));
            }
        });
    }
}
