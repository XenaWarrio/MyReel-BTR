package dx.queen.myreeltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    EditText etEmail;
    EditText etPassword;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.tv_toregistr).setOnClickListener(this);
        findViewById(R.id.bt_sign_in).setOnClickListener(this);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        if (email.isEmpty()) {
            etEmail.setError("Тут тип email должен быть");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){// if email isn`t a valid email
            etEmail.setError("Введи правильный email");
            etEmail.requestFocus();
            return;


        }

        if (password.isEmpty()) {
            etEmail.setError("Введи пароль, хитрюга");
            etEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etEmail.setError("Должно быть минимум 6 символов");
            etEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //go to activity
                    
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_toregistr:
                startActivity(new Intent(this , SignIn.class));
                break;
            case R.id.bt_sign_in:
               userLogin();
                break;
        }

    }
}