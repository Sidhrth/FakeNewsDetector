package com.example.kpk.fnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

        EditText shared = (EditText) findViewById(R.id.editText);
        shared.setText(sharedText);
    }

    public void check (View view){
       // Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show();
        EditText text = (EditText) findViewById(R.id.editText);
        String link = text.getText().toString();
        Intent i = new Intent(this,resultpage.class);
        i.putExtra("EXTRA_MESSAGAE",link);
        startActivity(i);


    }

    public void imgsearch(View view) {

    }
}
