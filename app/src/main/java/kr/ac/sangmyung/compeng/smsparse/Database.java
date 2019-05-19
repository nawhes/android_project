package kr.ac.sangmyung.compeng.smsparse;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.content.Context.MODE_PRIVATE;

public class Database extends Fragment {

    SharedPreferences query;
    /*
    query.xml
    key value 쌍
    "index" - 0부터 1씩 오르는 int값이며 다음으로 put 할 value의 index를 가지고 있다.
    index - table의 이름과 SMS에서 분석하게 될 String값이며 하나씩 put 될 때 마다 String.valueof(getInt("index",0))의 값을 id로 저장하며 index가 하나씩 오르게 설계되었다.
     */
    SQLiteDatabase db;
    /*
    smsparse.db
    SMS에서 분석하게 될 단어를 테이블 이름으로 가지며 그 단어가 포함된
    SMS의 발신자, 내용, 수신시각을 sender, contents, receivedDate 라는 엔트리로 접근한다.
     */
    EditText insertTableName;
    Spinner selectTableName;
    Button createTableBtn,deleteTableBtn;
    String createTableName;
    String deleteTableName;
    String[] items;

    public Database() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_database, container, false);

        //테이블과 query에 추가하고 싶은 단어를 추가하는 작업이다.
        //EditText로 단어를 받아들이며 단어를 생성한 후에는 fragment를 refresh한다.
        insertTableName = (EditText) rootView.findViewById(R.id.inputTableName);
        createTableBtn = (Button) rootView.findViewById(R.id.createTableBtn);
        createTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTableName = insertTableName.getText().toString();
                createTable(createTableName);
                createQuery(createTableName);
                insertTableName.setText("");
                refresh();
            }
        });

        //SMS에서 분석하게 될 단어를 삭제하는 작업이다.
        //현재 분석하고 있는 단어들을 spinner를 활용해 보여주고 그 중 선택된 단어가 deleteTableBtn에서 onClick될 때 작업이 이루어진다.
        query = getActivity().getSharedPreferences("query",MODE_PRIVATE);
        int index = query.getInt("index",0);
        //index만큼의 배열을 가진 String[] items에 index와 value값을 맵핑한다.
        items = new String[index];
        for (int i=0;i<index;i++) {
            items[i]=query.getString(String.valueOf(i),"");
        }
        //spinner에 items의 값을 입력하고 선택된 단어는 deleteTableName에 저장된다.
        selectTableName = (Spinner) rootView.findViewById(R.id.selectTableName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        selectTableName.setAdapter(adapter);
        selectTableName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deleteTableName = items[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //앞서 spinner에서 설정된 deleteTableName을 이용해서 db와 query를 수정하는 작업이다.
        //단어를 삭제한 이후에는 fragment를 refresh한다.
        deleteTableBtn = (Button) rootView.findViewById(R.id.deleteTableBtn);
        deleteTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTable(deleteTableName);
                deleteQuery(deleteTableName);
                refresh();
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

    public void createTable(String name) {
        if(!name.isEmpty()) {//name 이 빈 문자열이 아닌 경우에만
            dbOpen();
            db.execSQL("create table if not exists " + name + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " sender text, "
                    + " contents text, "
                    + " receivedDate text);");
        }
    }

    public void deleteTable(String name) {
        if(!name.isEmpty()) {//name 이 빈 문자열이 아닌 경우에만
            dbOpen();
            db.execSQL("DROP TABLE  " + name);
        }
    }

    void dbOpen() {
        db = getActivity().openOrCreateDatabase("smsparse.db", MODE_PRIVATE ,null); //SMSParse 이름의 db생성
    }

    void createQuery(String name) {
        if(!name.isEmpty()) {//name 이 빈 문자열이 아닌 경우에만
            query = getActivity().getSharedPreferences("query",MODE_PRIVATE); // query.xml qeury문 모음
            SharedPreferences.Editor editor = query.edit();
            //현재 index값을 가져오고 그 index에 추가하고자 하는 단어를 저장한 후 index의 값을 하나 올린다.
            int i = query.getInt("index",0);
            editor.putString(String.valueOf(i),name);
            i++;
            editor.putInt("index",i);
            editor.commit();
        }
    }

    void deleteQuery(String name) {
        SharedPreferences.Editor editor = query.edit();

        //삭제하고자 하는 query의 value값을 받아오기 때문에 value값에 접근하기 위해서 탐색한다.
        int i = query.getInt("index",0);
        for(int a=0;a<i;a++) {
            String target = query.getString(String.valueOf(a),"");
            if (target.equals(name)) {//삭제하고자하는 name값을 발견하면
                for(int b=a;b<i-1;b++) {//해당하는 index에 다음 index의 value값을 입력하는 것을 반복
                    editor.putString(String.valueOf(b),query.getString(String.valueOf(b+1),""));
                }
                i--;//맨 마지막 index를 삭제한다.
                editor.putInt("index",i);
                editor.commit();
            }
        }
    }

    void refresh(){
        FragmentTransaction refresh = getFragmentManager().beginTransaction();
        refresh.detach(this).attach(this).commit();
    }
}
