package `in`.ceeq.lyte.data.room

import `in`.ceeq.lyte.data.room.dao.UserDao
import `in`.ceeq.lyte.data.room.entity.User
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(User::class), version = 1)
abstract class LyteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
