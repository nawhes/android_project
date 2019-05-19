package kr.ac.sangmyung.compeng.smsparse2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewSMS2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewSMS2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewSMS2 extends Fragment {
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
    Spinner spinner;
    String[] items;

    public ViewSMS2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewSMS2.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewSMS2 newInstance(String param1, String param2) {
        ViewSMS2 fragment = new ViewSMS2();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_view_sms2, container, false);

        query = getActivity().getSharedPreferences("query",MODE_PRIVATE);

        int index = query.getInt("index",0);
        items = new String[index];

        for (int i=0;i<index;i++) {
            items[i]=query.getString(String.valueOf(i),"");
        }

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

    void viewTable(String query){
        db = getActivity().openOrCreateDatabase("smsparse.db", Activity.MODE_PRIVATE ,null); //SMSParse 이름의 db생성
        Cursor c1 = db.rawQuery("select sender, contents, receivedDate " + "from " + query,null);

        LinearLayout dynamicLayout = (LinearLayout) getActivity().findViewById(R.id.dynamicLayout);
        dynamicLayout.setOrientation(LinearLayout.VERTICAL);
        dynamicLayout.removeAllViews(); //

        int recordCount = c1.getCount();
        for (int i=0;i<recordCount;i++){

            int R = new Random().nextInt(255);
            int G = new Random().nextInt(255);
            int B = new Random().nextInt(255);

            c1.moveToNext();
            String sender = c1.getString(0);
            String contents = c1.getString(1);
            String receivedDate = c1.getString(2);

            TextView senderView = new TextView(getActivity());
            senderView.setText(sender);
            senderView.setBackgroundColor(Color.argb(60,R,G,B));

            TextView contentsView = new TextView(getActivity());
            contentsView.setText(contents);
            contentsView.setBackgroundColor(Color.argb(60,R,G,B));

            TextView receivedDateView = new TextView(getActivity());
            receivedDateView.setText(receivedDate);
            receivedDateView.setBackgroundColor(Color.argb(60,R,G,B));

            dynamicLayout.addView(senderView);
            dynamicLayout.addView(contentsView);
            dynamicLayout.addView(receivedDateView);
        }
    }
}
