package com.e.notepad;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private NoteDatabase noteDatabase;
    private List<Note> notes;
    private RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //methods:
        initializeViews();
        displayList();

       }
       public void onNoteClick(int Pos)
       {

         //alert dialog box for update or delete
           new AlertDialog.Builder(MainActivity.this)

               .setItems(new  String[]{"Delete","Update"}, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       switch (i)
                       {
                           case 0:
                               noteDatabase.getNoteDao().delete(notes.get(Pos));
                               notes.remove(Pos);
                               listVisibility();
                               break;
                           case 1:
                               MainActivity.this.pos=Pos;
                               startActivityForResult(
                                       new Intent(MainActivity.this,AddNoteActivity.class)
                                               .putExtra("note",notes.get(Pos)),100);
                               break;
                       }

                   }
               }).show();

       }

      public  void displayList()
      {
          noteDatabase=NoteDatabase.getInstance(MainActivity.this);
          new RetrieveTask(this).execute();
      }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Note>> {
        private WeakReference<MainActivity> activityWeakReference;
        // the only retain a weak reference to the activity
        RetrieveTask(MainActivity context)
        {
            activityWeakReference = new WeakReference<>(context);
        }


        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityWeakReference.get()!=null)
            {
                return activityWeakReference.get().noteDatabase.getNoteDao().getAll();
            }
            else
            return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes!=null&&notes.size()>0){

                activityWeakReference.get().notes.clear();
                activityWeakReference.get().notes.addAll(notes);
                //hide the empty text view
                activityWeakReference.get().textView.setVisibility(View.GONE);
                activityWeakReference.get().notesAdapter.notifyDataSetChanged();
            }
        }
    }
    private void initializeViews()
    {
        textView=findViewById(R.id.tv_empty);
        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        notes = new ArrayList<>();
        notesAdapter= new NotesAdapter(notes,MainActivity.this);
        recyclerView.setAdapter(notesAdapter);

    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(MainActivity.this,
                    AddNoteActivity.class),100);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                assert data != null;
                notes.add((Note) data.getSerializableExtra("note"));
            } else if (resultCode == 2) {
                assert data != null;
                notes.set(pos, (Note) data.getSerializableExtra("note"));
            }
            listVisibility();
        }
    }

    private void listVisibility()
    {
        int emptyMsgVisibility=View.GONE;
        if(notes.size()==0)
        {
            //no item display
            if(textView.getVisibility()==View.GONE)
                emptyMsgVisibility=View.VISIBLE;
        }
        textView.setVisibility(emptyMsgVisibility);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        NoteDatabase.cleanUp();
        super.onDestroy();
    }
}







