package com.example.edmol.webview;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity implements View.OnClickListener{
    private ConstraintLayout grafica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        grafica=findViewById(R.id.layoutGraf);
        grafica.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutGraf:
                startActivity(new Intent(home.this,webView.class));
                break;

        }
    }
}
