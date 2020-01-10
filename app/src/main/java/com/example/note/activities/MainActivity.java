package com.example.note.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.note.enums.Sorting;
import com.example.note.fragments.SortFindInterface;
import com.example.note.models.Note;
import com.example.note.R;

public class MainActivity extends AppCompatActivity {

    private SortFindInterface sortFind;

    private EditText tagFindEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.notesListFragment);
        sortFind = (SortFindInterface) fragment;

        Spinner spinner = findViewById(R.id.sortSpinner);
        ArrayAdapter<Sorting> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Sorting.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sorting sortType = (Sorting)parent.getItemAtPosition(position);
                sortFind.SortNotes(sortType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tagFindEdit = findViewById(R.id.tagFindEdit);
        tagFindEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                sortFind.FindByTag(editable.toString());
            }
        });
        tagFindEdit.clearFocus();
    }

    @Override
    public void onResume() {
        super.onResume();
        tagFindEdit.clearFocus();
    }

    public void addButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("note", new Note());
        startActivity(intent);
    }
}