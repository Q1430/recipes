package com.recipe.recipes.di

import android.app.Application
import androidx.room.Room
import com.recipe.recipes.MyApp
import com.recipe.recipes.data.local.FavoriteMealDao
import com.recipe.recipes.data.local.FavoriteMealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): FavoriteMealDatabase {
        return Room.databaseBuilder(
            app,
            FavoriteMealDatabase::class.java,
            "favorite_meals" // 数据库文件名
        ).build()
    }
    @Provides
    @Singleton
    fun provideFavoriteMealDao(db: FavoriteMealDatabase): FavoriteMealDao {
        return db.favoriteMealDao()
    }
}