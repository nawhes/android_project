package kr.ac.sangmyung.compeng.sampleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Private ProgressBar progressBar;
    Private TextView dataItem02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgrssBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(50);

        dataItem02 = (TextView) findViewById(R.id.dataItem02);
        dataItem02.setText("50%");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.incrementProgressBy(10);
                int currentProgress = progressBar.getProgress();

                dataItem02.setText(currentProgress + "%");
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                dataItem02.setText("0%");
            }
        });
    }
}
