package top.linxixiangxin.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class resultActicity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_acticity);
        listView = findViewById(R.id.lv_show);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<HashMap<String,Object>> myList;
        myList = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("result");
        SimpleAdapter myAdapter = new SimpleAdapter(this,myList,R.layout.list_item,new String[]{"Word","Describe"},new int[]{R.id.word,R.id.detail});
        listView.setAdapter(myAdapter);


    }
}
