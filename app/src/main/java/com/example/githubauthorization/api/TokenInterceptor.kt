package com.example.githubauthorization.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response



class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val request = originalHttpUrl.newBuilder()
            .addQueryParameter("AleksandrAlex", "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")
            .build()
        val url = originalRequest.newBuilder().url(request).build()
        return chain.proceed(url)
    }
}