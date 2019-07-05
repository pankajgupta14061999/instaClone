package com.example.clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3;
    Button but1,but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("The insta");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        but1=findViewById(R.id.but1);
        but2=findViewById(R.id.but2);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ParseUser username=new ParseUser();
                    username.setEmail(et1.getText().toString());
                    username.setUsername(et2.getText().toString());
                    username.setPassword(et3.getText().toString());
                    final ProgressDialog pro=new ProgressDialog(MainActivity.this);
                    pro.setMessage("Signing You Up");
                    pro.show();
                    username.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(MainActivity.this,"WELCOME "+ et2.getText(),Toast.LENGTH_SHORT).show();
                                pro.dismiss();
                                Intent intemnt =new Intent(MainActivity.this,proper.class);
                                startActivity(intemnt);
                            }
                            else
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
            }

        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
