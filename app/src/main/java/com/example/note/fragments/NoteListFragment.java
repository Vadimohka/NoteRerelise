package com.example.note.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.note.R;
import com.example.note.Sorting;
import com.example.note.activities.NoteActivity;
import com.example.note.adapters.NoteAdapter;
import com.example.note.adapters.NotesDatabaseAdapter;
import com.example.note.models.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment implements SortFindInterface {

    private List<Note> notes = new ArrayList<>();
    private AdapterView notesListView;
    private NoteAdapter noteAdapter;
    private Sorting sortType = Sorting.LastAdded;
    private String tagForSearching = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NotesDatabaseAdapter dbAdapter = new NotesDatabaseAdapter(getContext());
        dbAdapter.open();
        notes = dbAdapter.getNotes();
        if(!tagForSearching.equals(""))
            filterNotesByTag();
        noteAdapter = new NoteAdapter(getContext(), R.layout.note, notes);
        SortNotes(sortType);
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        notesListView = view.findViewById(R.id.notesList);


        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NoteActivity.class);
                intent.putExtra("note", notes.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NotesDatabaseAdapter dbAdapter = new NotesDatabaseAdapter(getContext());
        dbAdapter.open();
        notes = dbAdapter.getNotes();
        if(!tagForSearching.equals(""))
            filterNotesByTag();
        noteAdapter = new NoteAdapter(getContext(), R.layout.note, notes);
        SortNotes(sortType);
        notesListView = getView().findViewById(R.id.notesList);
        notesListView.setAdapter(noteAdapter);
        dbAdapter.close();
    }

    @Override
    public void SortNotes (Sorting sortType) {
        if (sortType == null)
            sortType = Sorting.LastAdded;
        noteAdapter.sortNotes(sortType);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void FindByTag (String tag) {
        tagForSearching = tag;
        NotesDatabaseAdapter dbAdapter = new NotesDatabaseAdapter(getContext());
        dbAdapter.open();
        notes.clear();
        notes.addAll(dbAdapter.getNotes());
        filterNotesByTag();
        dbAdapter.close();
        if(noteAdapter != null)
            noteAdapter.notifyDataSetChanged();
    }

    public void filterNotesByTag() {
        if (tagForSearching.equals("")) {
            SortNotes(sortType);
            return;
        }
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note: notes) {
            if (note.hasTag(tagForSearching.toLowerCase()))
                filteredNotes.add(note);
        }
        notes.clear();
        notes.addAll(filteredNotes);
        SortNotes(sortType);
    }
}