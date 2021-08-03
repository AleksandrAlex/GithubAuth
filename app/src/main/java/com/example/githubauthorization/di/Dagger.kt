package com.example.githubauthorization.di

import android.content.Context
import com.example.githubauthorization.ui.AuthFragment
import com.example.githubauthorization.GitHubApi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent{

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient{
//        return OkHttpClient
//            .Builder()
//                // как сделать эти поля динамическими?(я сделал статичесми)
//                // Я знаю что можно и через @Header в ретрофите задать (могу передать одно значение)
//                // , но как там передать "username:password" ???
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addInterceptor(TokenInterceptor())
//            .build()
//    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubApi.BASE_URL)
//                .client(client)
                .build()
    }

}