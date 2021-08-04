package com.example.githubauthorization.presentation

import android.app.Application
import com.example.githubauthorization.di.AppComponent
import com.example.githubauthorization.di.DaggerAppComponent

class App: Application() {


    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
//        appComponent.inject(this)

    }

}