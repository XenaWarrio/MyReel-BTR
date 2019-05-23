package Message;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import dx.queen.myreeltest.R;

public class MainMessenger extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    FloatingActionButton fab;

    TextView messageText, messageUser,messageTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_messenger);


        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input_message);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });


        displayChatMessage();

    }


    private void displayChatMessage() {
        ListView listOfMessage = findViewById(R.id.list_of_message);
//        Query query = FirebaseDatabase.getInstance().getReference();
//        FirebaseListOptiom <ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(query, ChatMessage.class)
//                .setLifecycleOwner(MainMessenger.this)
//                .setLayout(R.layout.list_item)
//                .build();
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class ,R.layout.list_item, FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //get references to the view of list item.xml
                messageText = v.findViewById(R.id.message_ext);
                messageUser = v.findViewById(R.id.message_user);
                messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM (HH:mm)", model.getMessageTime()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }
}
