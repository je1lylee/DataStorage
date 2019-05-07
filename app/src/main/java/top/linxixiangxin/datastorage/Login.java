package top.linxixiangxin.datastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private EditText username, passwordd;
    private CheckBox remember_pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.account);
        passwordd = findViewById(R.id.password);
        remember_pass = findViewById(R.id.remember_pass);
        login = findViewById(R.id.login);
        SharedPreferences sps = getSharedPreferences("login",MODE_PRIVATE);
        Boolean isSaved = sps.getBoolean("isSaved",false);
        if(isSaved){
            remember_pass.setChecked(true);
            username.setText(sps.getString("userName",""));
            passwordd.setText(sps.getString("passWord",""));
        }




        //EVENT
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = username.getText().toString(),
                        password = passwordd.getText().toString();
                if(account.equals("apple") && password.equals("666666"));{
                    if (remember_pass.isChecked()) {
                        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                        editor.putString("userName", account);
                        editor.putString("passWord", password);
                        editor.putBoolean("isSaved",remember_pass.isChecked());
                        editor.commit();
                    }
                    Intent intent = new Intent(Login.this,Welcome.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
