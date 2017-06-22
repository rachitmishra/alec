package `in`.ceeq.alec.provider.room.entity

import `in`.ceeq.alec.provider.room.entity.dao.UserDao
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
