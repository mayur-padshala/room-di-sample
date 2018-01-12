package com.novuslogics.roomexample.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.novuslogics.roomexample.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertMultipleRecord(User... users);

    @Insert
    void insertMultipleListRecord(List<User> userList);

    @Insert
    void insertOnlySingleRecord(User user);

    @Query("SELECT * FROM User")
    LiveData<List<User>> fetchAllUsers();

    @Query("SELECT * FROM User WHERE id=:user_id")
    LiveData<User> getSingleRecord(int user_id);

    @Update
    void updateRecord(User user);

    @Delete
    void deleteRecord(User user);

}
