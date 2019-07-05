package com.example.clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {

    EditText et4,et5;
    Button but3,but4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);
        but3=findViewById(R.id.but3);
        but4=findViewById(R.id.but4);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog prot=new ProgressDialog(login.this);
                prot.setMessage("Logging You In");
                prot.show();
                ParseUser.logInInBackground(et4.getText().toString(), et5.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user !=null && e==null)
                        {
                            Toast.makeText(login.this,"Welcome Back "+et4.getText().toString(),Toast.LENGTH_LONG).show();
                            prot.dismiss();
                            finish();
                            Intent intent =new Intent(login.this,proper.class);
                            startActivity(intent);

                        }
                    }
                });
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
