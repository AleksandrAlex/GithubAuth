package com.example.githubauthorization.di

import android.content.Context
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.api.TokenInterceptor
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.presentation.ProfileFragment
import com.example.githubauthorization.presentation.SearchRepositoryFragment
import com.example.githubauthorization.presentation.SearchRepositoryViewModelFactory
import com.example.githubauthorization.presentation.UserProfileViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Component(modules = [NetworkModule::class, ViewModelModule::class])
@Singleton
interface AppComponent{

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: SearchRepositoryFragment)

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

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(TokenInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubApi.BASE_URL)
                .client(client)
                .build()
    }

}

@Module
class ViewModelModule{

    @Singleton
    @Provides
    fun provideUserViewModelFactory(repository: UserRepository): UserProfileViewModelFactory {
        return UserProfileViewModelFactory(repository)
    }

    @Singleton
    @Provides
    fun provideSearchRepositoryViewModelFactory(repository: UserRepository): SearchRepositoryViewModelFactory{
        return SearchRepositoryViewModelFactory(repository)
    }
}


