package vn.kietnguyendev.translateapp.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.kietnguyendev.translateapp.core.database.TranslateAppDatabase

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesBookmarkDao(appDatabase: TranslateAppDatabase) = appDatabase.bookmarkDao()
}