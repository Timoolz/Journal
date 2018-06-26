package com.example.olamide.journal.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface DiaryDao {

    @Query("SELECT * FROM diary ORDER BY created_at")
    LiveData<List<DiaryEntry>> loadAllEntries();

    @Insert
    void insertEntry(DiaryEntry diaryEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(DiaryEntry diaryEntry);

    @Delete
    void deleteEntry(DiaryEntry diaryEntry);

    @Query("SELECT * FROM diary WHERE id = :id")
    LiveData<DiaryEntry> loadEntryById(int id);

}
