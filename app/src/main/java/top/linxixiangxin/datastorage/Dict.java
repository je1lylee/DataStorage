package top.linxixiangxin.datastorage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Dict extends AppCompatActivity {
    private EditText userWord, describe, keyWord;
    private Button insert, search;
    private DBHelper myHelper;
    private static final String TAG = "dialog";
    private String myRes;

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_insert:
                    insertWord();
                    break;
                case R.id.btn_search:
                    searchWord();
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

    protected void insertWord() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String word = userWord.getText().toString().trim();
        String detail = describe.getText().toString().trim();
        if (!word.equals("") && !detail.equals("") && word != null && detail != null) {
            //封装进 contentValue 再执行插入操作 插入是否成功
            ContentValues values = new ContentValues();
            values.put("Word", word);
            values.put("Describe", detail);
            long rowID = db.insert("dict", null, values);
            if (rowID != -1) {
                Toast.makeText(this, "第" + rowID + "行插入成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void searchWord() {
        //关键词，组织SQL语句，获取DB连接，查询，解析游标，回传结果
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String key = keyWord.getText().toString().trim();
        String columns[] = new String[]{"Word", "Describe"};
        String selecttion = "word like ?";
        String selectioinArgs[] = new String[]{"%" + key + "%"};
        Cursor cursor = db.query("dict", columns, selecttion, selectioinArgs, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            Log.d(TAG, "cursor长度: " + cursor.getCount());
            //有查询结果
            ArrayList<HashMap<String, Object>> resultList = convertCursorToList(cursor);
            Log.d(TAG, "查询结果：");
            Log.d(TAG, resultList.toString());
            //传递到结果页面展示
            Bundle data = new Bundle();
            data.putSerializable("result", resultList);
            Intent intent = new Intent(Dict.this, resultActicity.class);
            intent.putExtras(data);
            startActivity(intent);
        } else {
            //执行联网查询 并显示在主activity中 不进行页面跳转。
            Toast.makeText(this, "您查询的单词没有在数据库中，正在联网查询。", Toast.LENGTH_SHORT).show();
            userWord.setText(keyWord.getText().toString());
            try {
                String justTemp = getJsonWithOkHttp(keyWord.getText().toString());//因为执行异步，子线程有些慢 获取不到string
                Thread.sleep(2000);
                Log.d(TAG, "searchWord: " + justTemp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            describe.setText(justTemp);
        }

    }

    private String getJsonWithOkHttp(String word) {
        final String uurl = "http://dict-co.iciba.com/api/dictionary.php?w=" + word + "&type=json&key=A33F2CEACCB62615DE15339D6A5166C7";
        final String[] wordRes = new String[5];
        new Thread() {
            @Override
            public void run() {
                //1、创建一个OkHttpClient实例
                OkHttpClient client = new OkHttpClient();
                //2、创建Request对象，用于代表一条HTTP请求
                Request request = new Request.Builder()
                        .url(uurl)
                        .build();
                try {
                    //3、创建Call对象，调用execute()方法发送请求并获取服务器返回的数据
                    Response response = client.newCall(request).execute();
                    //4、打印获取的json数据
                    String result = response.body().string();
                    Log.d(TAG, "使用OKHttp获取到的JSON为: " + result);
                    //5、解析json
                    ArrayList<word> list = parseJsonByRawMethod(result);
                    Log.d(TAG, "解析出的list链表为：");
                    Log.d(TAG, giveMeString(list));
                    myRes = giveMeString(list);
                    wordRes[2] = myRes;
                    Log.d(TAG, "run: "+wordRes[2]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Log.d(TAG, "getJsonWithOkHttp: "+wordRes[2]);
        return wordRes[2];
    }

    private String giveMeString(ArrayList<word> list) {
        StringBuilder stringBuilder = new StringBuilder(); //要返回
        //stringBuilder.append("查询的单词为"+list.get(0).getWord_name()+",单词的释义为："); //提前存入单词名 -后来发现有些不合适
        for (int i = 0; i < list.size(); i++) {
            //遍历每个对象
            word Temp = list.get(i);
            stringBuilder.append(Temp.getPart());
            stringBuilder.append(Temp.getMeans() + ";");
        }
        String finalfinal = stringBuilder.toString();
        Log.d(TAG, "giveMeString: "+finalfinal);
        return finalfinal;
    }

    private ArrayList<word> parseJsonByRawMethod(String jsonData) {
        ArrayList<word> list = new ArrayList<word>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray symbols = jsonObject.getJSONArray("symbols");
            JSONObject sysbols_1 = symbols.getJSONObject(0);
            JSONArray parts = sysbols_1.getJSONArray("parts");
            for (int i = 0; i < parts.length(); i++) {
                word Word = new word();
                Word.setWord_name(jsonObject.getString("word_name"));//获取单词
                JSONObject description = parts.getJSONObject(i);
                Word.setPart(description.getString("part"));
                Word.setMeans(description.getString("means"));
                list.add(Word);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseJsonByRawMethod: " + list.toString());
        return list;
    }

    private ArrayList<HashMap<String, Object>> convertCursorToList(Cursor cursor) {
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        while (cursor.moveToNext()) {
            HashMap<String, Object> rowData = new HashMap<String, Object>();
            int wordIndex = cursor.getColumnIndex("Word");
            String Word = cursor.getString(wordIndex);
            int DescribeIndex = cursor.getColumnIndex("Describe");
            String Describe = cursor.getString(DescribeIndex);
            rowData.put("Word", Word);
            rowData.put("Describe", Describe);
            resultList.add(rowData);
        }
        cursor.close();
        myHelper.close();
        return resultList;

    }
}
