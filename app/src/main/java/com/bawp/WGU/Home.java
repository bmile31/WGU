package com.bawp.WGU;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button termsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        termsButton = findViewById(R.id.terms_button);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTerms();
            }
        });
    }

    public void openTerms(){
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }
}