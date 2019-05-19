package kr.ac.sangmyung.compeng.smsparse2;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Database2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Database2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Database2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SharedPreferences query;
    SQLiteDatabase db;
    EditText insertTableName;
    Spinner selectTableName;
    Button createTableBtn,deleteTableBtn;
    String createTableName;
    String deleteTableName;
    String[] items;

    public Database2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Database2.
     */
    // TODO: Rename and change types and number of parameters
    public static Database2 newInstance(String param1, String param2) {
        Database2 fragment = new Database2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_database2, container, false);

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

        query = getActivity().getSharedPreferences("query",MODE_PRIVATE);

        int index = query.getInt("index",0);
        items = new String[index];

        for (int i=0;i<index;i++) {
            items[i]=query.getString(String.valueOf(i),"");
        }

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void createTable(String name) {
        dbOpen();

        if(!name.isEmpty()) {//name 이 빈 문자열이 아닌 경우에만
            db.execSQL("create table if not exists " + name + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " sender text, "
                    + " contents text, "
                    + " receivedDate text);");
        }
    }

    void createQuery(String name) {
        if(!name.isEmpty()) {
            query = getActivity().getSharedPreferences("query",MODE_PRIVATE); // query.xml qeury문 모음
            SharedPreferences.Editor editor = query.edit();

            int i = query.getInt("index",0);

            editor.putString(String.valueOf(i),name);
            i++;
            editor.putInt("index",i);
            editor.commit();
        }
    }

    void deleteQuery(String name) {
        SharedPreferences.Editor editor = query.edit();

        int i = query.getInt("index",0);

        for(int a=0;a<i;a++) {
            String target = query.getString(String.valueOf(a),"");
            if (target.equals(name)) {
                for(int b=a;b<i-1;b++){
                    editor.putString(String.valueOf(b),query.getString(String.valueOf(b+1),""));
                }
                i--;
                editor.putInt("index",i);
                editor.commit();
            }
        }
    }

    public void deleteTable(String name) {
        db = getActivity().openOrCreateDatabase("smsparse.db", MODE_PRIVATE ,null); //SMSParse 이름의 db생성

        if(!name.isEmpty()) {//name 이 빈 문자열이 아닌 경우에만
            db.execSQL("DROP TABLE  " + name);
        }
    }

    void dbOpen() {
        db = getActivity().openOrCreateDatabase("smsparse.db", MODE_PRIVATE ,null); //SMSParse 이름의 db생성
    }

    void refresh(){
        FragmentTransaction refresh = getFragmentManager().beginTransaction();
        refresh.detach(this).attach(this).commit();
    }
}
