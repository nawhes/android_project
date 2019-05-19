package kr.ac.sangmyung.compeng.samplebuttonanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private Animation flowAnim;
    private Animation flowAnim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        flowAnim = AnimationUtils.loadAnimation(this, R.anim.flow);
        flowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(),"애니메이션 종료됨.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flowAnim2 = AnimationUtils.loadAnimation(this,R.anim.flow2);
        flowAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "애니메이션2 종료됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void onButton1Clicked(View v){
        textView.startAnimation(flowAnim);
        textView2.startAnimation(flowAnim2);
    }

    public void onButton2Clicked(View v){
        textView.clearAnimation();
    }

    public void onButton3Clicked(View v){
        textView2.clearAnimation();
    }
}
