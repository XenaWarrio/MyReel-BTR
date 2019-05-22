package search;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import dx.queen.myreeltest.R;
import search.Adapter.MyAdapter;
import search.Listener.IFirebaseLoad;
import search.Model.Person;

public class Search extends AppCompatActivity implements IFirebaseLoad  {

    ViewPager viewPager;
    MyAdapter adapter;

    IFirebaseLoad listener;

    FirebaseFirestore db;
    DocumentReference dude;

    CollectionReference people;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        
        setContentView(R.layout.activity_search);


        // init Firebase

        db = FirebaseFirestore.getInstance();
        dude = db.collection("users").document("user");
        //init event

        listener = this;

        people = FirebaseFirestore.getInstance().collection("users");

        viewPager = findViewById(R.id.view_pager);

        loadPersonInfo();
    }

    private void loadPersonInfo() {

        people.get().addOnFailureListener(e -> listener.onFirebaseLoadFailed(e.getMessage()))
                .addOnCompleteListener(task -> {

                    List<Person> personList = new ArrayList<>();

                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot personSnapshot : task.getResult()){
                            Person person = personSnapshot.toObject(Person.class);
                            personList.add(person);

                        }

                        listener.onFirebaseLoadSuccess(personList);
                    }
                });


    }

    @Override
    public void onFirebaseLoadSuccess(List<Person> personList) {
     adapter = new MyAdapter(this,personList);
     viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}
