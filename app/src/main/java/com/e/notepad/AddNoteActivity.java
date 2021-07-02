package com.e.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {
    private TextInputEditText et_title,et_content;
    private boolean update;
    Button button;

    //objects
    private NoteDatabase noteDatabase;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        //widgets
        et_title=findViewById(R.id.et_title);
        et_content=findViewById(R.id.et_content);
         button =findViewById(R.id.but_save);
         //objects
        noteDatabase = NoteDatabase.getInstance(AddNoteActivity.this);
        //check for correct object and data
        if((note=(Note)getIntent().getSerializableExtra("note"))!=null)
        {
            getSupportActionBar().setTitle("update Note");
            update=true;
            button.setText("Update");
            et_title.setText(note.getTitle());
            et_content.setText(note.getContent());
        }

        //handling button click event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if we need to update the notes
                if(update)
                {

                    note.setContent(et_content.getText().toString());
                    note.setTitle(et_title.getText().toString());
                    noteDatabase.getNoteDao().update(note);
                    setResult(note,2);
                }
                else
                {
                    note=new Note(et_content.getText().toString(),et_title.getText()
                    .toString());
                    new InsertTask(AddNoteActivity.this,note).execute();

                }
            }


        });

    }

    //setResult
    private void setResult(Note note,int flag)
    {
        setResult(flag,new Intent().putExtra("name",note));
        finish();
    }

    //InsertTask
    private static  class InsertTask extends AsyncTask<Void,Void,Boolean> {
        private WeakReference<AddNoteActivity> activityWeakReference;
        private Note note;

        //only retain a weakreference to the activity

        InsertTask(AddNoteActivity context,Note note)
        {
            activityWeakReference = new WeakReference<>(context);
            this.note=note;
        }

        //do in background method run on a worker thread


        @Override
        protected Boolean doInBackground(Void... voids) {
            //retriving auto incremented note id
            long j=activityWeakReference.get().noteDatabase.getNoteDao().insert(note);
            note.setNote_id((int) j);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
            {
                activityWeakReference.get().setResult(note,1);
                activityWeakReference.get().finish();
            }
        }
    }



}