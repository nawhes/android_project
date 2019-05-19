package kr.ac.sangmyung.compeng.doitmission_06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.btnLOGIN);
        Intent passedIntent = getIntent();
        String from = passedIntent.getStringExtra("from");
        Toast.makeText(this,from,Toast.LENGTH_LONG).show();

        final EditText id = (EditText) findViewById(R.id.editID);
        final EditText pw = (EditText) findViewById(R.id.editPW);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().length()==0 || pw.getText().length()==0) {
                    Toast.makeText(Login.super.getApplicationContext(),"ID 혹은 PW를 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    SimpleData data = new SimpleData("Login.java","Menu.class");
                    intent.putExtra(KEY_SIMPLE_DATA,data);
                    startService(intent);
                }
            }
        });
    }
}