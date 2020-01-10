package com.example.note.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.note.R;
import com.example.note.enums.Sorting;
import com.example.note.activities.NoteActivity;
import com.example.note.adapters.NoteAdapter;
import com.example.note.adapters.NotesDatabaseAdapter;
import com.example.note.models.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteListFragment extends Fragment
        implements SortFindInterface {

    private List<Note> notes = new ArrayList<>();
    private AdapterView notesListView;
    private NoteAdapter noteAdapter;
    private Sorting sortType = Sorting.LastAdded;
    private String tagForSearching = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        sortNotes();
        if(!tagForSearching.equals(""))
            filterNotesByTag();

        noteAdapter = new NoteAdapter(getContext(), R.layout.note, notes);
        notesListView = getView().findViewById(R.id.notesList);
        notesListView.setAdapter(noteAdapter);
        dbAdapter.close();
    }

    @Override
    public void SortNotes (Sorting sortType) {
        this.sortType = sortType;
        sortNotes();
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

    private void sortNotes() {
        switch (sortType) {
            case LastAdded:
            {

                notes.sort(new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });
            }
            break;
            case FirstAdded:
                notes.sort(new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });
                Collections.reverse(notes);
                break;
            case TitleAsc:
                notes.sort(new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
                    }
                });
                break;
            case TitleDesc:
            {
                notes.sort(new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
                    }
                });
                Collections.reverse(notes);
            }
            break;
        }
    }

    private void filterNotesByTag() {
        if (tagForSearching.equals("")) {
            sortNotes();
            return;
        }
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note: notes) {
            if (note.hasTag(tagForSearching.toLowerCase()))
                filteredNotes.add(note);
        }
        notes.clear();
        notes.addAll(filteredNotes);
        sortNotes();
    }
}