package kr.ac.sangmyung.compeng.smsparse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class SMSView extends Fragment {

    SharedPreferences query;
    SQLiteDatabase db;
    Spinner spinner;
    String[] items;

    public SMSView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_smsview, container, false);

        //단어를 통해 수집된 SMS를 보여주는 작업이다.
        //현재 분석하고 있는 단어들을 spinner를 활용해 보여주고 onItemSelected되면 해당 테이블의 내용의 개수만큼 동적생성해서 화면에 출력한다.
        query = getActivity().getSharedPreferences("query",MODE_PRIVATE);
        int index = query.getInt("index",0);
        items = new String[index];
        //index만큼의 배열을 가진 String[] items에 index와 value값을 맵핑한다.
        for (int i=0;i<index;i++) {
            items[i]=query.getString(String.valueOf(i),"");
        }
        //spinner에 items의 값을 입력하고 선택된 단어는 viewTable(String)의 파라미터로 넘겨진다.
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewTable(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void viewTable(String query){
        db = getActivity().openOrCreateDatabase("smsparse.db", Activity.MODE_PRIVATE ,null); //SMSParse 이름의 db생성
        //rawQuery()를 이용해서 query 테이블의 sender, contents, receivedDate값을 읽어들이며 그 값은 Cursor객체에 저장된다.
        Cursor c1 = db.rawQuery("select sender, contents, receivedDate " + "from " + query,null);

        LinearLayout dynamicLayout = (LinearLayout) getActivity().findViewById(R.id.dynamicLayout);
        dynamicLayout.setOrientation(LinearLayout.VERTICAL);
        dynamicLayout.removeAllViews(); //초기화작업

        /*
        c1의 첫번째 값은 레코드의 갯수를 int값으로 저장하고
        다음 값부터 레코드의 값을 읽어들인다. columnIndex를 이용해 접근하며 0부터 1씩 오른다.
        다음값으로 넘어가고자 할 땐 Cursor.moveToNext()를 이용한다.
         */
        int recordCount = c1.getCount();
        for (int i=0;i<recordCount;i++) {//레코드의 갯수만큼 반복

            int R = new Random().nextInt(255);
            int G = new Random().nextInt(255);
            int B = new Random().nextInt(255);

            c1.moveToNext();

            String sender = c1.getString(0);
            String contents = c1.getString(1);
            String receivedDate = c1.getString(2);

            TextView senderView = new TextView(getActivity());
            senderView.setText(sender);
            senderView.setBackgroundColor(Color.argb(60, R, G, B));//투명도가 설정된 랜덤으로 생성된 색상을 배경으로 설정한다.

            TextView contentsView = new TextView(getActivity());
            contentsView.setText(contents);
            contentsView.setTextSize(20);
            contentsView.setBackgroundColor(Color.argb(60, R, G, B));

            TextView receivedDateView = new TextView(getActivity());
            receivedDateView.setText(receivedDate);
            receivedDateView.setBackgroundColor(Color.argb(60, R, G, B));

            dynamicLayout.addView(senderView);
            dynamicLayout.addView(contentsView);
            dynamicLayout.addView(receivedDateView);
        }
    }
}
