package com.example.note.fragments;

import com.example.note.enums.Sorting;

public interface SortFindInterface {
    void SortNotes(Sorting sortType);
    void FindByTag(String tag);
}
