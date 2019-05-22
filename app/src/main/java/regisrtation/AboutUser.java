package regisrtation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dx.queen.myreeltest.Menu;
import dx.queen.myreeltest.R;

public class AboutUser extends AppCompatActivity {
    EditText etName;
    EditText etAge;
    EditText etAboutYourself;

    String name;
    String age;
    String aboutUser;


    ImageButton ib_Further;


    FirebaseFirestore db;
    Map<String , Object> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about_user);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etAboutYourself = findViewById(R.id.et_about_yourself);

        ib_Further = findViewById(R.id.ib_further);

        name = etName.getText().toString();
         age = etAge.getText().toString();
         aboutUser = etAboutYourself.getText().toString();

        db = FirebaseFirestore.getInstance();

        ib_Further.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName == null && etAge != null && etAboutYourself != null) {
                    etName.setError("Я знаю ты в бегах и скрываешься, но нам имя свое можешь сказать");
                    etName.requestFocus();
                    return;
                }

                if (etAge == null) {
                    etAge.setError("Скажи спасибо, что мы автоматически не поставили тебе возраст , о которорм кричат твои морщины");
                    etAge.requestFocus();
                    return;
                }

                if (etAboutYourself == null) {
                    etAboutYourself.setError("Но постой, кто же ТЫ?");
                    etAboutYourself.requestFocus();
                    return;

                }
                addData();
                Intent intent =  new Intent(AboutUser.this ,Menu.class);
                startActivity(intent);
            }
        });
    }

    private void addData(){
        name = etName.getText().toString();
        age = etAge.getText().toString();
        aboutUser = etAboutYourself.getText().toString();

        user = new HashMap<>() ;

        user.put("name", name);
        user.put("age", age);
        user.put("aboutUser", aboutUser);
        user.put("sex" , ChooseSex.sex);

        db.collection("users").document("user").set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(AboutUser.this, "Всё ок!",
                        Toast.LENGTH_SHORT).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AboutUser.this, "Что-то пошло не так" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
}


