package com.example.note.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import com.example.note.R;
import com.example.note.Sorting;
import com.example.note.models.Note;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NoteAdapter extends ArrayAdapter<Note> {
    private LayoutInflater inflater;
    private int layoutResourceId;
    private List<Note> notes;


    public NoteAdapter(Context context, int resource, List<Note> notes ) {
        super(context, resource, notes);
        this.notes = notes;
        this.layoutResourceId = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        NoteVIew noteView;

        if (convertView == null) {

            convertView = inflater.inflate(layoutResourceId, parent, false);

            noteView = new NoteVIew();
            noteView.titleView = convertView.findViewById(R.id.titleView);
            noteView.tagsView = convertView.findViewById(R.id.tagsView);
            noteView.bodyView = convertView.findViewById(R.id.bodyView);

            convertView.setTag(noteView);

        } else {
            noteView = (NoteVIew) convertView.getTag();
        }

        Note note = notes.get(position);

        noteView.titleView.setText(note.getTitle());
        noteView.tagsView.setText(note.getStringOfTags());
        noteView.bodyView.setText(note.getBody());

        return convertView;
    }

    public void sortNotes(Sorting sortType) {
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
}