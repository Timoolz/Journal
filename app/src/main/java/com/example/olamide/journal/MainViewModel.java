package com.example.olamide.journal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.olamide.journal.database.DiaryEntry;
import com.example.olamide.journal.database.JournalDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainViewModel extends AndroidViewModel{

    private static final String TAG = MainViewModel.class.getSimpleName();

    private FirebaseAuth mAuth;


    private LiveData<List<DiaryEntry>> entries;

    public MainViewModel(Application application) {
        super(application);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        JournalDatabase database = JournalDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the entries from the DataBase");

        if(mAuth.getCurrentUser()!= null){

            entries = database.diaryDao().loadAllEntriesByUid(mAuth.getCurrentUser().getUid());
        }else {
            entries = database.diaryDao().loadAllEntries();
        }

    }

    public LiveData<List<DiaryEntry>> getEntries() {
        return entries;
    }

}
