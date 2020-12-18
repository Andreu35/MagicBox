package com.are.magicbox.di

import android.content.Context
import com.are.magicbox.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.are.magicbox.data.local.AppDatabase
import com.are.magicbox.data.local.dao.FavoriteDao
import com.are.magicbox.data.pref.MagicBoxPreferences
import com.are.magicbox.data.remote.MagicBoxDataSource
import com.are.magicbox.data.remote.net.MagicBoxAPI
import com.are.magicbox.data.repository.MagicBoxRepository
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
        .baseUrl(BuildConfig.BASE_URL)
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