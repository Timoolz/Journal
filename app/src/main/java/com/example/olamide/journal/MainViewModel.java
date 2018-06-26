package com.example.olamide.journal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.olamide.journal.database.DiaryEntry;
import com.example.olamide.journal.database.JournalDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel{

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<DiaryEntry>> entries;

    public MainViewModel(Application application) {
        super(application);
        JournalDatabase database = JournalDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the entries from the DataBase");
        entries = database.diaryDao().loadAllEntries();
    }

    public LiveData<List<DiaryEntry>> getEntries() {
        return entries;
    }

}
