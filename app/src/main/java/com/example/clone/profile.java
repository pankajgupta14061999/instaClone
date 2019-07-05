package com.example.clone;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {
    EditText p1,p2,p3,p4,p5;
    Button button;


    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        p1=view.findViewById(R.id.p1);
        p2=view.findViewById(R.id.p2);
        p3=view.findViewById(R.id.p3);
        p4=view.findViewById(R.id.p4);
        p5=view.findViewById(R.id.p5);
        button=view.findViewById(R.id.button);
       final ParseUser parseuser= ParseUser.getCurrentUser();
       if(parseuser.get("Name")==null){
           p1.setText("");}
       else if(parseuser.get("Name") != null)
       { p1.setText(parseuser.get("Name")+"");}


        if(parseuser.get("Age")==null){
            p1.setText("");}
        else
        { p1.setText(parseuser.get("Age").toString());}
        if(parseuser.get("Book")==null){
            p1.setText("");}
        else
        { p1.setText(parseuser.get("Book")+"");}
        if(parseuser.get("App")==null){
            p1.setText("");}
        else
        { p1.setText(parseuser.get("App")+"");}
        if(parseuser.get("Sport")==null){
            p1.setText("");}
        else
        { p1.setText(parseuser.get("sport")+"");}


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseuser.put("Name",p1.getText().toString());
                parseuser.put("Age",p2.getText().toString());
                parseuser.put("Book",p3.getText().toString());
                parseuser.put("App",p4.getText().toString());
                parseuser.put("sport",p5.getText().toString());
                final ProgressDialog pot=new ProgressDialog(getContext());
                pot.setMessage("Updating Data");
                pot.show();
                parseuser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"Data Updated ",Toast.LENGTH_LONG).show();
                            pot.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            pot.dismiss();
                        }
                    }
                });
            }
        });



        return view;
    }

}
