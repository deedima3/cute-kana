package com.cutekana.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cutekana.data.local.CuteKanaDatabase
import com.cutekana.data.local.SeedData
import com.cutekana.data.ml.StrokeRecognizer
import com.cutekana.data.repository.*
import com.cutekana.data.tts.KanaTextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CuteKanaDatabase {
        return Room.databaseBuilder(
            context,
            CuteKanaDatabase::class.java,
            "cutekana_database"
        )
        .fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Database will be populated on first access via Application class
            }
        })
        .build()
    }
    
    @Provides
    @Singleton
    fun provideCharacterRepository(database: CuteKanaDatabase): CharacterRepository {
        return CharacterRepositoryImpl(database.characterDao())
    }
    
    @Provides
    @Singleton
    fun provideUserProgressRepository(
        database: CuteKanaDatabase,
        @ApplicationContext context: Context
    ): UserProgressRepository {
        return UserProgressRepositoryImpl(
            database.userProgressDao(),
            database.achievementDao(),
            context.getSharedPreferences("high_scores", Context.MODE_PRIVATE)
        )
    }
    
    @Provides
    @Singleton
    fun provideMockTestRepository(database: CuteKanaDatabase): MockTestRepository {
        return MockTestRepositoryImpl(database.mockTestDao())
    }
    
    @Provides
    @Singleton
    fun provideStrokeRecognizer(@ApplicationContext context: Context): StrokeRecognizer {
        return StrokeRecognizer(context)
    }
    
    @Provides
    @Singleton
    fun provideTextToSpeech(@ApplicationContext context: Context): KanaTextToSpeech {
        return KanaTextToSpeech(context)
    }
}