package com.hyj.demo.viewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class EdittextActivity extends AppCompatActivity {
    private EditText editText;
    private MyEditText myEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        editText = findViewById(R.id.edittext);
        myEditText = findViewById(R.id.myEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("---->", "onEditorAction: ");
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("---->", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("---->", "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("---->", "afterTextChanged: ");

            }
        });
        myEditText.setOnFinishComposingListener(new MyEditText.OnFinishComposingListener() {
            @Override
            public void finishComposing() {
                Log.d("---->", "finishComposing: ");
            }
        });

    }
}

