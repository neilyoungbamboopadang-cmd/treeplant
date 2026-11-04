package com.example.treeplanting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> login());

        findViewById(R.id.tvGoRegister).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        findViewById(R.id.tvForgot).setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter your email first");
                return;
            }
            auth.sendPasswordResetEmail(email).addOnCompleteListener(t ->
                    Toast.makeText(this, t.isSuccessful()
                            ? "Reset link sent to " + email
                            : "Failed: " + t.getException().getMessage(),
                            Toast.LENGTH_LONG).show());
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.setError("Invalid email"); return; }
        if (pass.isEmpty()) { etPassword.setError("Enter password"); return; }

        btnLogin.setEnabled(false);
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            btnLogin.setEnabled(true);
            if (task.isSuccessful()) {
                Intent i = new Intent(this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Toast.makeText(this, "Login failed: " + task.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
