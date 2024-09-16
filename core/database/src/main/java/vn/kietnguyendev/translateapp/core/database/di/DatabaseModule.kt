package vn.kietnguyendev.translateapp.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vn.kietnguyendev.translateapp.core.database.TranslateAppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TranslateAppDatabase::class.java,
        "translate_app_database"
    ).build()
}