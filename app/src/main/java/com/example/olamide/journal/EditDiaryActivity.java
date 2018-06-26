package com.example.olamide.journal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olamide.journal.database.DiaryEntry;
import com.example.olamide.journal.database.JournalDatabase;

import java.util.Date;

public class EditDiaryActivity extends AppCompatActivity {



    public static final String EXTRA_DIARY_ENTRY_ID = "extraDiaryEntryId";

    public static final String INSTANCE_DIARY_ENTRY_ID = "instanceDiaryEntryId";


    private static final int DEFAULT_DIARY_ENTRY_ID = -1;

    private static final String TAG = EditDiaryActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText;
    EditText mMultiEditText;
    Button mButton;

    private int mDiaryEntryId = DEFAULT_DIARY_ENTRY_ID;

    // Member variable for the Database
    private JournalDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);


        initViews();

        mDb = JournalDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DIARY_ENTRY_ID)) {
            mDiaryEntryId = savedInstanceState.getInt(INSTANCE_DIARY_ENTRY_ID, DEFAULT_DIARY_ENTRY_ID);
        }




        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DIARY_ENTRY_ID)) {
            mButton.setText(R.string.update_button);
            if (mDiaryEntryId == DEFAULT_DIARY_ENTRY_ID) {
                // populate the UI
                mDiaryEntryId = intent.getIntExtra(EXTRA_DIARY_ENTRY_ID, DEFAULT_DIARY_ENTRY_ID);


                EditDiaryViewModelFactory factory = new EditDiaryViewModelFactory(mDb, mDiaryEntryId);

                final EditDiaryViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(EditDiaryViewModel.class);


                viewModel.getDiaryEntry().observe(this, new Observer<DiaryEntry>() {
                    @Override
                    public void onChanged(@Nullable DiaryEntry diaryEntry) {
                        viewModel.getDiaryEntry().removeObserver(this);
                        populateUI(diaryEntry);
                    }
                });
            }
        }


    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_DIARY_ENTRY_ID, mDiaryEntryId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mEditText = findViewById(R.id.edit_mood);
        mMultiEditText = findViewById(R.id.edit_diary_content);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }


    private void populateUI(DiaryEntry entry) {
        if (entry == null) {
            return;
        }

        mEditText.setText(entry.getMood());
        mMultiEditText.setText(entry.getDiaryContent());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String mood = mEditText.getText().toString();
        String diaryContent = mMultiEditText.getText().toString();
        Date date = new Date();

        final DiaryEntry entry = new DiaryEntry(mood, diaryContent, date, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mDiaryEntryId == DEFAULT_DIARY_ENTRY_ID) {
                    // insert new task
                    mDb.diaryDao().insertEntry(entry);
                } else {
                    //update task
                    DiaryEntry originalEntry = mDb.diaryDao().loadEntry(mDiaryEntryId);

                    entry.setId(mDiaryEntryId);
                    entry.setCreatedAt(originalEntry.getCreatedAt());
                    mDb.diaryDao().updateEntry(entry);
                }
                finish();
            }
        });
    }

}
