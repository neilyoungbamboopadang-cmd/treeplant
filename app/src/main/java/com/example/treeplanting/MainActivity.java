package com.example.treeplanting;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Already logged in? Skip straight to Home.
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnGetStarted).setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
        findViewById(R.id.tvBegin).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}
