package com.example.roomnotesapp.di

import android.content.Context
import androidx.room.Room
import com.example.roomnotesapp.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context)=Room.databaseBuilder(context,NotesDatabase::class.java, "NotesDatabase").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase)=db.notesDao()


}