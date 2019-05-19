package kr.ac.sangmyung.compeng.sampleparcelable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private TextView textView;
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = (TextView)findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "Park");

                Log.d("MenuActivity","good");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        if (intent != null) {
            Bundle bundle = intent.getExtras();

            SimpleData data = (SimpleData) bundle.getParcelable(KEY_SIMPLE_DATA);

            Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();        }
    }
}
