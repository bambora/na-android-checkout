/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.beanstream.payform.demo.R;


public class DemoActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView tokenText = (TextView)findViewById(R.id.demo_pay_token);
        tokenText.setText("Token: new token");
        tokenText.setVisibility(View.VISIBLE);
    }
}
