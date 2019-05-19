package kr.ac.sangmyung.compeng.doitmission_06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Customer extends AppCompatActivity {
    public static final String KEY_SIMPLE_DATA = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent passedIntent = getIntent();
        String from = passedIntent.getStringExtra("from");
        Toast.makeText(this,from,Toast.LENGTH_LONG).show();

        Button btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                SimpleData data = new SimpleData("Customer.java", "Menu.class");
                intent.putExtra(KEY_SIMPLE_DATA, data);
                startService(intent);
            }
        });
        Button btnLOGIN = (Button) findViewById(R.id.btnLOGIN);
        btnLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                SimpleData data = new SimpleData("Customer.java", "Login.class");
                intent.putExtra(KEY_SIMPLE_DATA, data);
                startService(intent);
            }
        });
    }
}
