package com.example.githubauthorization

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class NetworkConnection: BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver", "InflateParams")
    override fun onReceive(context: Context, intent: Intent?) {


        if (!isNetworkConnected(context)){
            Toast.makeText(context, "Sorry, Don't have internet connection!", Toast.LENGTH_LONG).show()
            Log.d("isConnect", "Sorry, Don't have internet connection!")

            val view = LayoutInflater.from(context).inflate(R.layout.no_signal_layout, null)

            val dialog = AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create()

            dialog.show()

            val retryBtn = view.findViewById<Button>(R.id.retryBtn)

            retryBtn.setOnClickListener {
                dialog.dismiss()
                onReceive(context, intent)
            }


        }
        else{
            Toast.makeText(context, "You have internet!!!", Toast.LENGTH_LONG).show()
            Log.d("isConnect", "You have internet!!!")
        }
    }

     private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}