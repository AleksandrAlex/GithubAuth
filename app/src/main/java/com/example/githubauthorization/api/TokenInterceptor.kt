package com.example.githubauthorization.api

import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response



class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
            .newBuilder()
            .addHeader("AleksandrAlex", "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")
            .build()

        val originalHttpUrl = originalRequest.url
        val request = originalHttpUrl.newBuilder()
            .build()
        val url = originalRequest.newBuilder().url(request).build()
        return chain.proceed(url)
    }
}