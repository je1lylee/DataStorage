package top.linxixiangxin.datastorage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dict extends AppCompatActivity {
    private EditText userWord, describe, keyWord;
    private Button insert, search;
    private DBHelper myHelper;

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_insert:
                    insertWord();
                    break;
                case R.id.btn_search:
                    break;
                default:
                    Toast.makeText(Dict.this, "选择错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        myHelper = new DBHelper(this);
        userWord = findViewById(R.id.et_word);
        describe = findViewById(R.id.et_detail);
        keyWord = findViewById(R.id.et_key);
        insert = findViewById(R.id.btn_insert);
        search = findViewById(R.id.btn_search);
        MyClickListener myClickListener = new MyClickListener();
        insert.setOnClickListener(myClickListener);
        search.setOnClickListener(myClickListener);
    }
    protected void insertWord(){
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String word = userWord.getText().toString().trim();
        String detail = describe.getText().toString().trim();
        if(!word.equals("") && !detail.equals("") && word!= null && detail != null){
            //封装进 contentValue 再执行插入操作 插入是否成功
            ContentValues values = new ContentValues();
            values.put("Word",word);
            values.put("Describe",detail);
            long rowID = db.insert("dict",null,values);
            if (rowID!= -1){
                Toast.makeText(this, "第"+rowID+"行插入成功！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void searchWord(){
        //关键词，组织SQL语句，获取DB连接，查询，解析游标，回传结果
    }
}
