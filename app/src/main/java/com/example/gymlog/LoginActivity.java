package com.example.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymlog.Database.GymLogRepository;
import com.example.gymlog.Database.entities.User;
import com.example.gymlog.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private GymLogRepository repository;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verifyUser()) {
                    Toast.makeText(LoginActivity.this, "Invalid cred", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        if (username.isEmpty()) {
            return false;
        }
        User user = repository.getUserByUserName(username);

        if (user != null) {
            String password = binding.passwordLoginEditText.getText().toString();
            if (password.equals(user.getPassword())) {
                return true;
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
        return false;
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}