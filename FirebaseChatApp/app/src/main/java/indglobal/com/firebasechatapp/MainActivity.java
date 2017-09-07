package indglobal.com.firebasechatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends  AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    ListView listOfMessages;
    private String loggedInUserEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfMessages= (ListView)findViewById(R.id.list_of_messages);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in_item/sign up activity
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in_item. Therefore, display a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getEmail(),
                    Toast.LENGTH_LONG)
                    .show();

            displayChatMessages();
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail())
                        );

                // Clear the input
                input.setText("");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in_item. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();

                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in_item. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        }
    }
    public String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }
    private void displayChatMessages() {

        loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Log.d("Main", "user id: " + loggedInUserEmail);

        adapter = new MessageAdapter(this, ChatMessage.class, R.layout.in_item,
                FirebaseDatabase.getInstance().getReference());
        listOfMessages.setAdapter(adapter);
    }
}