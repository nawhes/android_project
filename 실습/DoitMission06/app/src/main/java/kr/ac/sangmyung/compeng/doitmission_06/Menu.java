package kr.ac.sangmyung.compeng.doitmission_06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent passedIntent = getIntent();
        String from = passedIntent.getStringExtra("from");
        Toast.makeText(this,from,Toast.LENGTH_LONG).show();

        Button btnCustomer = (Button) findViewById(R.id.btnCustomer);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                SimpleData data = new SimpleData("Menu.java","Customer.class");
                intent.putExtra(KEY_SIMPLE_DATA,data);
                startService(intent);
            }
        });
        Button btnSales = (Button) findViewById(R.id.btnSales);
        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                SimpleData data = new SimpleData("Menu.java","Sales.class");
                intent.putExtra(KEY_SIMPLE_DATA,data);
                startService(intent);
            }
        });
        Button btnProduct = (Button) findViewById(R.id.btnProduct);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                SimpleData data = new SimpleData("Menu.java","Product.class");
                intent.putExtra(KEY_SIMPLE_DATA,data);
                startService(intent);
            }
        });
    }
}