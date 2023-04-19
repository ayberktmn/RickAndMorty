package com.ayberk.rickandmortyy.di.module

import android.content.Context
import android.content.SharedPreferences
import com.ayberk.rickandmorty20.prefs.SessionManager
import com.ayberk.rickandmorty20.util.Constants
import com.ayberk.rickandmortyy.di.retrofit.RetrofitServiceInstance

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    var baseURL = "https://rickandmortyapi.com/api/"

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context ) = context.getSharedPreferences(
        Constants.PREF_NAME,Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSessionManager(preferences: SharedPreferences) = SessionManager(preferences)

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit) : RetrofitServiceInstance
    {
        return retrofit.create(RetrofitServiceInstance::class.java)
    }
    @Singleton
    @Provides
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



}