package com.kappdev.wordbook.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kappdev.wordbook.core.domain.util.term_to_speech.TermToSpeech
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase
import com.kappdev.wordbook.feature_dictionary.data.repository.DictionaryRepositoryImpl
import com.kappdev.wordbook.feature_dictionary.data.repository.SettingsRepositoryImpl
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.*
import com.kappdev.wordbook.feature_dictionary.domain.util.FlashCardsOptionsImpl
import com.kappdev.wordbook.feature_dictionary.presentation.tests.TestsOptions
import com.kappdev.wordbook.feature_dictionary.presentation.writing.WritingOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideDictionaryDatabase(app: Application) : DictionaryDatabase {
        return Room.databaseBuilder(
            app,
            DictionaryDatabase::class.java,
            DictionaryDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @ViewModelScoped
    fun provideDictionaryRepository(db: DictionaryDatabase) : DictionaryRepository {
        return DictionaryRepositoryImpl(db.setDao, db.termDao)
    }

    @Provides
    @ViewModelScoped
    fun provideDictionaryUseCases(repository: DictionaryRepository,@ApplicationContext appContext: Context) : DictionaryUseCases {
        return DictionaryUseCases(
            addSet = AddSet(repository),
            insertTerm = InsertTerm(appContext, repository),
            removeSet = RemoveSet(repository),
            removeTerm = RemoveTerm(repository),
            updateSet = UpdateSet(repository),
            getAllSets = GetAllSets(repository),
            getTermsFlowBySetId = GetTermsFlowBySetId(repository),
            getTermsListBySetId = GetTermsListBySetId(repository),
            getSetById = GetSetById(repository),
            getTermById = GetTermById(repository),
            getImageFromStorage = GetImageFromStorage(appContext),
            getImageFromUrl = GetImageFromUrl(appContext),
            proposeTermMeaning = ProposeTermMeaning(appContext),
            clearTable = ClearTable(repository),
            moveTermToSet = MoveTermToSet(repository),
            getTermsByListOfSetsIds = GetTermsByListOfSetsIds(repository)
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSettingRepository(@ApplicationContext appContext: Context): SettingsRepository {
        return SettingsRepositoryImpl(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideFlashCardsOptions(@ApplicationContext appContext: Context): FlashCardsOptionsImpl {
        return FlashCardsOptionsImpl(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideTestsOptions(@ApplicationContext appContext: Context): TestsOptions {
        return TestsOptions(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideWritingOptions(@ApplicationContext appContext: Context): WritingOptions {
        return WritingOptions(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideTermToSpeech(@ApplicationContext appContext: Context): TermToSpeech {
        return TermToSpeech(appContext, SettingsRepositoryImpl(appContext))
    }

    @Provides
    @ViewModelScoped
    fun provideImExDatabaseUseCases(
        @ApplicationContext appContext: Context,
        db: DictionaryDatabase,
        repository: DictionaryRepository
    ): ImExDatabaseUseCases {
        return ImExDatabaseUseCases(
            importDatabase = ImportDatabase(appContext, repository),
            exportDatabase = ExportDatabase(appContext, db),
            exportSet = ExportSet(appContext, repository),
            importSet = ImportSet(appContext, repository)
        )
    }
}