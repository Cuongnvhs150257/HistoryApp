package com.fpt.university.apphistory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.fpt.university.apphistory.History;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM history")
    List<History> getListHistory();

    @Delete
    void deleteHistory(History history);
}
