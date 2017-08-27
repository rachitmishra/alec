package `in`.ceeq.lyte.data.contentprovider

open class BaseColumns {

    val id = "_id"

    val integer = "integer"

    val real = "real"

    val default = "default"

    val text = "text"

    val notNull = "not null"

    val primaryKey = "primary key"

    val autoIncrement = "autoincrement"

    val unique = "unique"

    fun create(tableName: String, columns: String, unique: String) =
            "create table $tableName ($id $integer $primaryKey $autoIncrement, $columns, $unique)"

    fun drop(tableName: String) = "drop table if exists $tableName"
}
