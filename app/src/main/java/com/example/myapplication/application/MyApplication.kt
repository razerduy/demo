package com.example.myapplication.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.myapplication.BuildConfig


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        fun isInDebugMode(): Boolean {
            return BuildConfig.DEBUG
        }
    }
}