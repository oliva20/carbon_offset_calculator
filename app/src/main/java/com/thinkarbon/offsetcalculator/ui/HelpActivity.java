package com.thinkarbon.offsetcalculator.ui;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thinkarbon.offsetcalculator.R;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("Reduce your Emissions");
        TextView tv = (TextView) findViewById(R.id.meat_text);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tv2 = (TextView) findViewById(R.id.offset_text);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
