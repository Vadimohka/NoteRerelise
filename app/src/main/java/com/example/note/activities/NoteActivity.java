package com.example.note.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note.models.Note;
import com.example.note.adapters.NotesDatabaseAdapter;
import com.example.note.R;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    EditText titleEdit;
    EditText tagsEdit;
    EditText bodyEdit;
    TextView updateTime;

    private NotesDatabaseAdapter adapter;

    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEdit = findViewById(R.id.titleEdit);
        tagsEdit = findViewById(R.id.tagsEdit);
        bodyEdit = findViewById(R.id.bodyEdit);
        updateTime = findViewById(R.id.lastUpdate);
        adapter = new NotesDatabaseAdapter(this);

        fillEdits();

        if (bodyEdit.getText().toString().equals(""))
        {
            MaterialButton btn = findViewById(R.id.delete_button);
            btn.setVisibility(View.GONE);

            MaterialButton btn2 = findViewById(R.id.save_button);
            ViewGroup.LayoutParams params = btn2.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            btn2.setLayoutParams(params);
        }
    }

    private void fillEdits() {
        Bundle arguments = getIntent().getExtras();

        if (arguments == null)
        {
            return;
        }
        note = (Note)arguments.getSerializable("note");

        if (note == null)
        {
            return;
        }

        if (note.hasTitle())
            titleEdit.setText(note.getTitle());
        tagsEdit.setText(note.getStringOfTags());
        bodyEdit.setText(note.getBody());
        String lastUpdate = "Last update: " + note.getUpdateDate();
        updateTime.setText(lastUpdate);
    }

    public void save(View view){
        String title = titleEdit.getText().toString();
        String body = bodyEdit.getText().toString();
        String tags = tagsEdit.getText().toString();

        if (body.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.bodyNotFilledError),
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        note = new Note(note.getId(), title, body, tags);
        note.setUpdateDate(new Date());
        adapter.open();
        if (note.getId() != 0)
            adapter.update(note);
        else
            adapter.insert(note);

        MaterialButton btnSave = findViewById(R.id.save_button);
        MaterialButton btnDelete = findViewById(R.id.delete_button);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnSave.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(btnDelete.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        adapter.close();
        goBack();
    }

    public void delete(View view){
        if (note.getId() == 0)
            goBack();

        adapter.open();
        adapter.delete(note.getId());
        adapter.close();
        goBack();
    }

    private void goBack(){
        this.onBackPressed();
    }
}
