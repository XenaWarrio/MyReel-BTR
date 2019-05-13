package regisrtation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import dx.queen.myreeltest.R;

public class AddingPhotosActivity extends AppCompatActivity{

    private static final int PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 111;


    ImageView imageCamera;
   Button buttonSave;
   ProgressBar  progressBar;

    Uri uriProfileImage;
    String imageUrl;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_adding_photos);
        auth = FirebaseAuth.getInstance();

        imageCamera = findViewById(R.id.iv_camera);
        buttonSave = findViewById(R.id.bt_save);
        progressBar = findViewById(R.id.progress_bar);

        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE );
                    Intent myIntent = new Intent();
                    myIntent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    startActivity(myIntent);
                }
                showImageChooser();
                    }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveUserInformation();
                  Intent intent = new Intent(AddingPhotosActivity.this ,ChooseSex.class );
                   startActivity(intent);
                }
            });

  }


    private void saveUserInformation() {
        FirebaseUser user = auth.getCurrentUser();
        if(user != null && imageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(imageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(AddingPhotosActivity.this , "Профайл обновлен" , Toast.LENGTH_SHORT)
                                .show();
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE  && resultCode == RESULT_OK  &&  data != null && data.getData() != null ){
           uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
               imageCamera.setImageBitmap(bitmap);
               uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference imageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+ System.currentTimeMillis()+ ".jpg");
        if(uriProfileImage != null){
            progressBar.setVisibility(View.VISIBLE);
            imageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.GONE);

                                imageUrl = imageRef.getDownloadUrl().toString();// instead of taskSnapshot.getDownloadUrl, which is outdated

                            }
                        })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddingPhotosActivity.this , e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            });

        }


    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выбери фотографию" ), PICK_IMAGE);
    }

  @Override
    public  void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQUEST_CODE)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                showImageChooser();
            else{
                Toast.makeText(this, "Нам нужно разрешение, красавчик", Toast.LENGTH_SHORT).show();
            }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

  }

}
