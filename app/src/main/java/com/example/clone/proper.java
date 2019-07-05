package com.example.clone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class proper extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
     ViewPager viewPager;
    private tabAdapter tabAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proper);
        setTitle("The Insta");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         viewPager=findViewById(R.id.viewPager);
         tabAdapter=new tabAdapter(getSupportFragmentManager());
         viewPager.setAdapter(tabAdapter);

         tabLayout=findViewById(R.id.tabLayout);
         tabLayout.setupWithViewPager(viewPager,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.postImage)
        {
            if(android.os.Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(proper.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            else
            {
                captureimage();
            }
        } else if(item.getItemId()==R.id.fulllogout)
        {
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent=new Intent(proper.this,MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if(requestCode==3000)
       {
           if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
           {
               captureimage();
           }
       }
    }
    private void captureimage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==4000 && resultCode==RESULT_OK  && data!=null ){

            try {
                Uri capturedImage =data.getData();
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),capturedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("img.png", bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture", parseFile);
               // parseObject.put("image_des", q1.getText().toString());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading...");
                dialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(proper.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(proper.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
