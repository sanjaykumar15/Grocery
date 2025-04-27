package com.sanjay.grocery.di

import android.app.Application
import android.content.Context
import com.sanjay.grocery.core.Constants
import com.sanjay.grocery.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun providesRealm(@ApplicationContext context: Context): Realm {
        Realm.init(context)
        val realmConfiguration =
            RealmConfiguration.Builder()
                .name(Constants.RealmCons.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun providesApiClient(context: Context): ApiClient {
        return ApiClient(context)
    }
}