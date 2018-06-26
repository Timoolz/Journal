package com.example.olamide.journal;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.olamide.journal.database.JournalDatabase;

public class EditDiaryViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final JournalDatabase mDb;
    private final int mEntryId;


    public EditDiaryViewModelFactory(JournalDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new EditDiaryViewModel(mDb, mEntryId);
    }
}
