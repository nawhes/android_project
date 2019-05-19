package kr.ac.sangmyung.compeng.smsparsing;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SMSReceiver extends BroadcastReceiver {
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SQLiteDatabase db;
    SharedPreferences query;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 인텐트 안에 들어있는 SMS 메시지를 파싱합니다.
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages != null && messages.length > 0) {
            // SMS 발신 번호 확인
            String sender = messages[0].getOriginatingAddress();
            // SMS 메시지 확인
            String contents = messages[0].getMessageBody().toString();
            // SMS 수신 시간 확인
            Date receivedDate = new Date(messages[0].getTimestampMillis());

            insertSMS(context, sender, contents, receivedDate);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCount = objs.length;
        for (int i = 0; i < smsCount; i++) {
            // PDU 포맷으로 되어 있는 메시지를 복원합니다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // API 23 이상
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }


    void insertSMS(Context context, String sender, String contents, Date receivedDate) {
        db = context.openOrCreateDatabase("smsparse.db", MODE_PRIVATE ,null); //SMSParse 이름의 db생성
        query = context.getSharedPreferences("query",MODE_PRIVATE); // query.xml qeury문 모음

        int i;
        int index = query.getInt("index",0);

        for(i=0;i<index;i++) {
            String a = query.getString(String.valueOf(i), "");
            if (contents.contains(a)) {
                ContentValues recordValues = new ContentValues();
                recordValues.put("sender", sender);
                recordValues.put("contents", contents);
                recordValues.put("receivedDate", receivedDate.toString());
                db.insert(a, null, recordValues);
            }
        }
    }
}

