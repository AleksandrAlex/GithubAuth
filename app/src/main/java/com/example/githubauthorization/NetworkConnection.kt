package com.example.githubauthorization

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class NetworkConnection: BroadcastReceiver() {

    private val _hasConnect = MutableLiveData<Boolean>()
    val hasConnect: LiveData<Boolean> = _hasConnect

    @SuppressLint("UnsafeProtectedBroadcastReceiver", "InflateParams")
    override fun onReceive(context: Context, intent: Intent?) {

         _hasConnect.postValue(isNetworkConnected(context))

    }

     private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}