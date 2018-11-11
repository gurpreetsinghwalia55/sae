package com.example.dell.sae.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.R;
import com.example.dell.sae.callbacks.ILoginCallback;
import com.example.dell.sae.models.Credential;
import com.example.dell.sae.services.AuthenticationService;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareViews();
    }

    private void prepareViews() {
        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginProgressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(usernameEditText.getText())) {
                    usernameEditText.setError("Username can't be empty");
                    loginProgressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(passwordEditText.getText())) {
                    passwordEditText.setError("Password can't be empty");
                    loginProgressBar.setVisibility(View.GONE);
                    return;
                }

                AuthenticationService service = new AuthenticationService();
                Credential credential = new Credential();
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                credential.setUsername(username);
                credential.setPassword(password);
                service.login(credential, new ILoginCallback() {
                    @Override
                    public void onLogin(boolean result) {
                        if (result) {
                            Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                            intent.putExtra(Constants.EXTRA_TEACHER_CODE, username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                        loginProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
