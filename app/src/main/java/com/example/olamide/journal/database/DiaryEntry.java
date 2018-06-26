package com.example.olamide.journal.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diary")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mood;
    private String diaryContent;
    @ColumnInfo(name = "created_at")
    private Date createdAt;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public DiaryEntry(String mood, String diaryContent, Date createdAt, Date updatedAt){

        this.mood = mood;
        this.diaryContent = diaryContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public DiaryEntry(int id, String mood, String diaryContent, Date createdAt, Date updatedAt){

        this.id =id;
        this.mood = mood;
        this.diaryContent = diaryContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
