package id.rllyhz.dicodingsubmissionbfaa.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.rllyhz.dicodingsubmissionbfaa.api.GithubApi
import id.rllyhz.dicodingsubmissionbfaa.data.local.GithubDatabase
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail.UserDetailRepository
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainRepository
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav.UserFavRepository
import id.rllyhz.dicodingsubmissionbfaa.util.DispacherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubAPI(): GithubApi =
        Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)


    @Provides
    @Singleton
    fun provideGithubDB(
        application: Application
    ): GithubDatabase =
        Room.databaseBuilder(application, GithubDatabase::class.java, "github_database")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideMainRepository(
        githubApi: GithubApi,
        application: Application
    ): MainRepository =
        MainRepository(githubApi, application)

    @Provides
    @Singleton
    fun provideUserDetailRepository(
        githubApi: GithubApi,
        application: Application,
        db: GithubDatabase,
        dispachers: DispacherProvider
    ): UserDetailRepository =
        UserDetailRepository(githubApi, application, db, dispachers)

    @Provides
    @Singleton
    fun provideUserFavRepository(
        db: GithubDatabase
    ): UserFavRepository =
        UserFavRepository(db)


    @Provides
    @Singleton
    fun provideDispatchers(): DispacherProvider = object : DispacherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}