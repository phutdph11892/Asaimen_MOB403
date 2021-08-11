package com.example.asaimen.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.asaimen.R;

public class LienHeActivity extends AppCompatActivity {
    Toolbar toolbarLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lien_he );
        toolbarLienHe=findViewById( R.id.toolbarLienHe );
        ActionToobar();
    }

    private void ActionToobar() {
        setSupportActionBar( toolbarLienHe );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarLienHe.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }
}