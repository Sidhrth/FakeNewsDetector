package com.example.kpk.fnd;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

        private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        }


    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            EditText shared = (EditText) findViewById(R.id.editText);
            shared.setText(sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        ImageView imageView = (ImageView) findViewById(R.id.displayimage);
        imageView.setImageURI(imageUri);
        imageView.setVisibility(View.VISIBLE);
        }
    }

    public void check (View view){
       // Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show();
        EditText text = (EditText) findViewById(R.id.editText);
        String link = text.getText().toString();
        ImageView img = (ImageView) findViewById(R.id.displayimage);
        img.buildDrawingCache();
        Bitmap bitmap = img.getDrawingCache();

        Intent i = new Intent(this,resultpage.class);
        i.putExtra("EXTRA_MESSAGAE",link);
        i.putExtra("BitmapImage", bitmap);
        startActivity(i);


    }

    public void imgadd(View view) {


        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    public void login(View view)
    {

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.displayimage);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setVisibility(View.VISIBLE);
            }
        }
}

