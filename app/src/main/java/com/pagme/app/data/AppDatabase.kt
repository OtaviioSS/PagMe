package com.pagme.app.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import br.com.alura.orgs.database.converter.Converters
import com.pagme.app.data.dao.DebtDao
import com.pagme.app.data.dao.UserDao
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.model.User


@Database(
    entities = [Debt::class, User::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 3, to = 4, spec = AppDatabase.MyAutoMigration::class
        )
    ]

)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    @RenameColumn(
        tableName = "Debt",
        fromColumnName = "idCard",
        toColumnName = "idUser"
    )
    class MyAutoMigration : AutoMigrationSpec

    abstract fun debtDao(): DebtDao
    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var database: AppDatabase? = null
        fun instance(context: Context): AppDatabase {
            return database ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "pagme.db"
            ).build().also {
                database = it
            }
        }
    }

}