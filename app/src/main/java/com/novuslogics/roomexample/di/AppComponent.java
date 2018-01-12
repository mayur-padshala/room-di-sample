package com.novuslogics.roomexample.di;

import com.novuslogics.roomexample.di.module.RoomModule;
import com.novuslogics.roomexample.pages.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

}