package top.linxixiangxin.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

public class file_persist extends AppCompatActivity {
    private EditText editText;
    private Button saveToFile,SaveToSD,readData,readSD;
    private TextView result;

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
    }
    private void saveDataToInner(String content){
        FileOutputStream out = null;
        out = openFileOutput("innerData")
    }
}
