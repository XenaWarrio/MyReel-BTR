package dx.queen.myreeltest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "TAG";


    TextView tvName;
    TextView tvSex;
    TextView tvAbout;
    ImageView profilePhoto;


    String name;
    String sex;
    String about;


    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);


        tvName = findViewById(R.id.tv_name);
        tvSex = findViewById(R.id.tv_sex);
        tvAbout = findViewById(R.id.tv_about);

        profilePhoto = findViewById(R.id.tv_image_profile);

        db = FirebaseFirestore.getInstance();
        readData();
    }




    public void readData() {
        DocumentReference user = db.collection("users").document("user");
        user.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                //Log.d(TAG, " " + doc.getData());

                                name = (doc.get("name")).toString();
                                tvName.setText(name);

                                sex = (doc.get("sex")).toString();
                                tvSex.setText(sex);

                                about = (doc.get("aboutUser")).toString();
                                tvAbout.setText(about);

                            } else {
                                Toast.makeText(ProfileActivity.this, "Пожалуйста, купите новый телефон", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }




    }
