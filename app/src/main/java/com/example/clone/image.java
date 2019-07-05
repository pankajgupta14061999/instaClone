package com.example.clone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class image extends Fragment implements View.OnClickListener {
    Button button2;
    EditText q1;
    ImageView img1;
    Bitmap  recievedImageBitmap;


    public image() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        img1=view.findViewById(R.id.img1);
        q1=view.findViewById(R.id.q1);
        button2=view.findViewById(R.id.button2);
        img1.setOnClickListener(image.this);
        button2.setOnClickListener(image.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img1:
                if(android.os.Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else{
                    getChosenImage();
                }
                break;
            case R.id.button2:
               if(recievedImageBitmap != null) {
                   if (q1.getText().toString().equals("")) {
                       Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_LONG).show();
                   } else {
                       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                       recievedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                       byte[] bytes = byteArrayOutputStream.toByteArray();
                       ParseFile parseFile = new ParseFile("img.png", bytes);
                       ParseObject parseObject = new ParseObject("Photo");
                       parseObject.put("picture", parseFile);
                       parseObject.put("image_des", q1.getText().toString());
                       parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                       final ProgressDialog dialog = new ProgressDialog(getContext());
                       dialog.setMessage("Uploading...");
                       dialog.show();
                       parseObject.saveInBackground(new SaveCallback() {
                           @Override
                           public void done(ParseException e) {
                               if (e == null) {
                                   Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
                                   dialog.dismiss();
                               } else {
                                   Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                                   dialog.dismiss();
                               }
                           }
                       });
                   }
               }
                   else{
                           Toast.makeText(getContext(), "Please select a Image", Toast.LENGTH_LONG).show();
                       }

                       break;

        }
    }

    private void getChosenImage() {
        Toast.makeText(getContext(),"Now You can share your Image",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000)
        {
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try
                {
                    Uri selectedImage =data.getData();
                    String[] filePathColumn={MediaStore.Images.Media.DATA};
                    Cursor cursor =getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnindex =cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath =cursor.getString(columnindex);
                    cursor.close();
                    recievedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    img1.setImageBitmap(recievedImageBitmap);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
