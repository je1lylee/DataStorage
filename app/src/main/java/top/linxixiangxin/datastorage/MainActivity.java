package top.linxixiangxin.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView demolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demolist = findViewById(R.id.example_list);

        ArrayAdapter<String> example_adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_gallery_item,
                getResources().getStringArray(R.array.example_info_array));
        demolist.setAdapter(example_adp);
        //set action listener
        demolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //哪一个adapter,点的哪一个控件，

                Toast.makeText(MainActivity.this, "您将要学习的是："+((TextView)view).getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                switch (i){
                    case 0:
                        intent.setClass(MainActivity.this,share_pre.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this,Login.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this,file_persist.class);
                        startActivity(intent);
                    default:
                        Toast.makeText(MainActivity.this, "#暂不可用#", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
