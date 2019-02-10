package com.example.androidnotepadnodejs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnotepadnodejs.util.Datetime;
import com.example.androidnotepadnodejs.util.HttpAddNotesAsync;
import com.example.androidnotepadnodejs.util.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtNoteDatetime;
    TextView toolbarTitle;
    EditText txtNote;
    EditText txtNoteTitle;
    String noteTitle = "";
    String noteContent = "";
    Datetime datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        datetime = new Datetime(getCurrentTimeStamp());
        //
        toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar();

        //
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("New note");

        //
        txtNoteDatetime = findViewById(R.id.new_note_datetime);
        txtNoteDatetime.setText(datetime.getDate() +", "+ datetime.getTime());

        //
        txtNoteTitle = findViewById(R.id.title);
        txtNoteTitle.setFocusableInTouchMode(true);
        txtNoteTitle.setFocusable(true);
        txtNoteTitle.requestFocus();

        //
        txtNote = findViewById(R.id.note_txt);
        txtNote.setMovementMethod(new ScrollingMovementMethod());

        txtNoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteTitle = s.toString().trim();
                Log.d("noteTitle", noteTitle);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteContent = s.toString().trim();
                Log.d("noteContent", noteContent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                // User chose the "Settings" item, show the app settings UI...
                if(noteTitle.isEmpty() && noteContent.isEmpty()){
                    SnackBar("Enter title and content");
                }
                else if(noteTitle.isEmpty()){
                    SnackBar("Enter title");
                }else if(noteContent.isEmpty()) {
                    SnackBar("Enter content");
                }else{
                    postNoteToServer(noteTitle, noteContent);
                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void SnackBar(String text){
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        snackbar.setDuration(2200);
        snackbar.show();
        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    // Snackbar closed on its own
                    try{
                        Thread.sleep(450);
                        // Then do something meaningful...
                        Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                        startActivity(intent);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public void postNoteToServer(String title, String noteTxt){
        datetime = new Datetime(getCurrentTimeStamp());

        Note note = new Note(null, title, noteTxt, getCurrentTimeStamp());

        AsyncTask<String, Void, Integer> addNote = new HttpAddNotesAsync(new HttpAddNotesAsync.AsyncResponse() {
            @Override
            public void processFinish(int result) {
                Log.d("resultCode",Integer.toString(result));
                switch (result){
                    case 200:
                        Log.d("output","200");
                        SnackBar("Note added successfully");
                        break;
                    case 204:
                        SnackBar("Server did not provide a response to your request");
                        break;
                    case 404:
                        SnackBar("Request was unable to be process, due to error in syntax");
                        break;
                }
            }
        },this, note);
        addNote.execute();
    }
}

