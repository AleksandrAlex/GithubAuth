package com.example.githubauthorization

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class NetworkUtilTest {


    @Test
    fun isNetworkConnected_return_true() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val expect = networkInfo != null && networkInfo.isConnectedOrConnecting
        assert(expect)
    }

    @Test
    fun isNetworkConnected_return_false() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val expect = networkInfo != null && networkInfo.isConnectedOrConnecting
        assertNotSame(expect, false)
    }
}