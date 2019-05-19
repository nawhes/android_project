package kr.ac.sangmyung.compeng.samplelifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Main onStart 호출", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Main onStop 호출", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Main onDestroy 호출", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Main onPause 호출", Toast.LENGTH_LONG).show();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Main onResume 호출", Toast.LENGTH_LONG).show();
        restoreState();
    }

    public void onButton1Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        startActivity(intent);
    }

    private void saveState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", nameInput.getText().toString());
        editor.commit();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if ( (pref != null) && (pref.contains("name")) ) {
            String name = pref.getString("name","");
            nameInput.setText(name);
        }
    }
}


