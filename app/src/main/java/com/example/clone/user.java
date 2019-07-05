package com.example.clone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class user extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    TextView gone;



    public user() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        listView=view.findViewById(R.id.listview);
        arrayList = new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
        gone =view.findViewById(R.id.gone);
        listView.setOnItemClickListener(user.this);
        listView.setOnItemLongClickListener(user.this);


        ParseQuery<ParseUser> parseQuery= ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e == null)
                {
                    if(users.size() > 0)
                    {
                        for(ParseUser user : users)
                        {
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                        gone.animate().alpha(0).setDuration(1000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getContext(),userpost.class);
        intent.putExtra("username",arrayList.get(i));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
       ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
       parseQuery.whereEqualTo("username",arrayList.get(i));
       parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
           @Override
           public void done(ParseUser user, ParseException e) {
               if(user != null && e==null)
               {
                   final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                   prettyDialog.setTitle(user.getUsername() + " 's Info")
                           .setMessage(user.get("Name") + "\n"
                                   + user.get("Age") + "\n"
                                   + user.get("Book") + "\n"
                                   + user.get("App")+"\n"
                                    +user.get("sport"))
                           .setIcon(R.drawable.person)
                           .addButton(
                                   "OK",     // button text
                                   R.color.pdlg_color_white,  // button text color
                                   R.color.pdlg_color_green,  // button background color
                                   new PrettyDialogCallback() {  // button OnClick listener
                                       @Override
                                       public void onClick() {
                                           // Do what you gotta do
                                           prettyDialog.dismiss();
                                       }
                                   }
                           ).show();
               }
           }
       });


        return true;
    }
}
