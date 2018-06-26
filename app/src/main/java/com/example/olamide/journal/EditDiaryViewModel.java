package com.example.olamide.journal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.olamide.journal.database.DiaryEntry;
import com.example.olamide.journal.database.JournalDatabase;

public class EditDiaryViewModel extends ViewModel {

    private LiveData<DiaryEntry> entry;


    public EditDiaryViewModel(JournalDatabase database, int diaryEntryId) {
        entry = database.diaryDao().loadEntryById(diaryEntryId);
    }


    public LiveData<DiaryEntry> getDiaryEntry() {
        return entry;
    }


}
