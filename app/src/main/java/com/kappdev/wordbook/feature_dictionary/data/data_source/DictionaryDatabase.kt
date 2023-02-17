package com.kappdev.wordbook.feature_dictionary.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.model.Term

@Database(
    entities = [Set::class, Term::class],
    version = 4,
    autoMigrations = [
        AutoMigration (from = 3, to = 4, spec = DictionaryDatabase.Migration3To4::class),
    ]
)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract val setDao: SetDao
    abstract val termDao: TermDao

    companion object {
        const val SHM = "-shm"
        const val WAL = "-wal"

        const val DATABASE_NAME = "dictionary_database"
        const val DATABASE_SHM = "dictionary_database-shm"
        const val DATABASE_WAL = "dictionary_database-wal"
    }

    @DeleteColumn (tableName = "terms_table",columnName = "id")
    @DeleteColumn (tableName = "sets_table",columnName = "id")
    class Migration3To4 : AutoMigrationSpec

}