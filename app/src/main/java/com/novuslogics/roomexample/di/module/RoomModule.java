package com.novuslogics.roomexample.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.novuslogics.roomexample.dao.UserDao;
import com.novuslogics.roomexample.db.SampleDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class RoomModule {

    @Singleton
    @Provides
    SampleDatabase providesRoomDatabase(Application application) {
        return Room.databaseBuilder(application, SampleDatabase.class, "demo_database").build();
    }

    @Singleton
    @Provides
    UserDao userDao(SampleDatabase sampleDatabase) {
        return sampleDatabase.daoAccess();
    }


}
