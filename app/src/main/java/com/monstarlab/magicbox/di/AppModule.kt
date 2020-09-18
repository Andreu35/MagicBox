package com.monstarlab.magicbox.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.monstarlab.magicbox.data.local.AppDatabase
import com.monstarlab.magicbox.data.local.dao.FavoriteDao
import com.monstarlab.magicbox.data.pref.MagicBoxPreferences
import com.monstarlab.magicbox.data.remote.MagicBoxDataSource
import com.monstarlab.magicbox.data.remote.net.MagicBoxAPI
import com.monstarlab.magicbox.data.repository.MagicBoxRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(com.monstarlab.magicbox.BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGSON(): Gson = GsonBuilder().create()

    @Provides
    fun provideAPIService(retrofit: Retrofit): MagicBoxAPI = retrofit.create(MagicBoxAPI::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: MagicBoxAPI, @ApplicationContext appContext: Context) = MagicBoxDataSource(api, appContext)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MagicBoxDataSource, localDataSource: FavoriteDao) = MagicBoxRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) = MagicBoxPreferences(appContext)
}