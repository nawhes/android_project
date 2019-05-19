package kr.ac.sangmyung.compeng.samplereceiver;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위험 권한 부여 요청
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS 수신 권한 있음.", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "SMS 수신 권한 없음",Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this,"SMS 권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else  {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS},1);
            }
        }
    }
}
