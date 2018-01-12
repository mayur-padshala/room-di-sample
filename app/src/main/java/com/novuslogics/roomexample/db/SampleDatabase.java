package com.novuslogics.roomexample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.novuslogics.roomexample.dao.UserDao;
import com.novuslogics.roomexample.model.User;

@Database(entities = {User.class}, version = 1)

public abstract class SampleDatabase extends RoomDatabase {

    public abstract UserDao daoAccess();

}
