package com.example.githubauthorization.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.githubauthorization.presentation.fragments.DetailRepositoryFragment
import com.example.githubauthorization.presentation.fragments.FavoriteDetailsRepositoryFragment
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.Repository
import com.example.githubauthorization.api.TokenInterceptor
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.RepoDB
import com.example.githubauthorization.domain.UserProfileViewModel
import com.example.githubauthorization.presentation.*
import com.example.githubauthorization.presentation.fragments.AuthFragment
import com.example.githubauthorization.presentation.fragments.FavoriteRepositoryFragment
import com.example.githubauthorization.presentation.fragments.ProfileFragment
import com.example.githubauthorization.presentation.fragments.SearchRepositoryFragment
import com.example.githubauthorization.presentation.viewmodels.ViewModelKey
import dagger.*
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Component(modules = [NetworkModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class])
@Singleton
interface AppComponent{

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: SearchRepositoryFragment)

    fun inject(fragment: AuthFragment)

    fun inject(fragment: DetailRepositoryFragment)

    fun inject(fragment: FavoriteRepositoryFragment)

    fun inject(fragment: FavoriteDetailsRepositoryFragment)

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
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
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
abstract class ViewModelModule{


    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun provideUserViewModelFactory(viewModel: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchRepositoryViewModel::class)
    abstract fun provideSearchRepositoryViewModelFactory(viewModel: SearchRepositoryViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FavoriteRepositoryViewModel::class)
    abstract fun provideFavoriteRepositoryViewModelFactory(viewModel: FavoriteRepositoryViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailRepositoryFragmentViewModel::class)
    abstract fun provideDetailRepositoryFragmentViewModelFactory(viewModel: DetailRepositoryFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteDetailsRepositoryViewModel::class)
    abstract fun provideFavoriteDetailsRepositoryViewModelFactory(viewModel: FavoriteDetailsRepositoryViewModel): ViewModel
}

@Module
class DatabaseModule{
    @Singleton
    @Provides
    fun provideDatabase(context: Context): RepoDB{
        return RepoDB.invoke(context)
    }
}

@Module
interface RepositoryModule{

    @Binds
    fun provideRepository(userRepository: UserRepository): Repository
}


