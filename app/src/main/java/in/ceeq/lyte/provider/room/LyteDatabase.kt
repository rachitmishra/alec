package `in`.ceeq.lyte.provider.room

import `in`.ceeq.lyte.provider.room.entity.User
import `in`.ceeq.lyte.provider.room.entity.dao.UserDao
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(User::class), version = 1)
abstract class LyteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
