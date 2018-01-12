package com.novuslogics.roomexample;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.novuslogics.roomexample.di.AppComponent;
import com.novuslogics.roomexample.di.DaggerAppComponent;
import com.novuslogics.roomexample.di.module.AppModule;

import timber.log.Timber;

public class RoomExampleApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent(AppCompatActivity activity) {
        return ((RoomExampleApplication) activity.getApplication()).component;
    }
}
