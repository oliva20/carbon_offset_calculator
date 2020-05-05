package com.thinkarbon.offsetcalculator.ui;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thinkarbon.offsetcalculator.R;


public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");
        TextView tv = (TextView) findViewById(R.id.link_terms_conditions);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
