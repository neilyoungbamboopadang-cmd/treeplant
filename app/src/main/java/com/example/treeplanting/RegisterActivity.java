package com.example.treeplanting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText etEmail, etPass, etConfirm;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etRegEmail);
        etPass = findViewById(R.id.etRegPassword);
        etConfirm = findViewById(R.id.etRegConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
        findViewById(R.id.tvGoLogin).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void register() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString();
        String confirm = etConfirm.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.setError("Invalid email"); return; }
        if (pass.length() < 6) { etPass.setError("At least 6 characters"); return; }
        if (!pass.equals(confirm)) { etConfirm.setError("Passwords do not match"); return; }

        btnRegister.setEnabled(false);
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            btnRegister.setEnabled(true);
            if (task.isSuccessful()) {
                Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Toast.makeText(this, "Registration failed: " + task.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
