package pl.training.goodweather.configuration

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.training.commons.logging.AndroidLogger
import pl.training.commons.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun logger(): Logger = AndroidLogger()

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): ApplicationDatabase
        = Room.databaseBuilder(context, ApplicationDatabase::class.java, "goodweather")
            .fallbackToDestructiveMigration()
            .build()

}