package com.example.treeplanting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            ((TextView) findViewById(R.id.tvUser)).setText(getString(R.string.signed_in_as, user.getEmail()));
        }

        View.OnClickListener logoutListener = v -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        };

        findViewById(R.id.btnLogout).setOnClickListener(logoutListener);
        findViewById(R.id.btnLogoutBottom).setOnClickListener(logoutListener);
    }
}
