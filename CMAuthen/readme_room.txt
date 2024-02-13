Tutorial
- https://tonyowen.medium.com/room-entity-annotations-379150e1ca82
- https://guides.codepath.com/android/Room-Guide


- Edit builld.gradle

apply plugin: 'kotlin-kapt'
add plugin

- add dependencies
// Room database and compiler
implementation "androidx.room:room-runtime:2.5.1"
kapt "androidx.room:room-compiler:2.5.1"
// Coroutines support for Room
implementation "androidx.room:room-ktx:2.5.1"


- add model, here is an example
@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "level") val level: Int? = 0,
)

- add DAO by using room annotation @Dao, @Insert, @Query...
@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun query(username:String):User
}


- register table, here is an example
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}

- get database instance
mDB.value = databaseBuilder(context, AppDatabase::class.java, "idkubDB")
                .addMigrations(MIGRATION_2_3)
                .build()

-
val card = getDB().cardDao().getCardById(id)


ChatGPT (credit)
-------------------------------------------
Sure! Here are the commonly used annotations in Room:

1. `@Entity`: Specifies that a class represents a table in the database.
2. `@PrimaryKey`: Marks a field as the primary key of the entity.
3. `@ColumnInfo`: Defines metadata for a column in an entity table.
4. `@Ignore`: Excludes a field from being persisted in the database.
5. `@ForeignKey`: Defines a foreign key relationship between two entities.
6. `@Embedded`: Specifies that a field should be treated as an embedded field.
7. `@Dao`: Marks an interface or abstract class as a Data Access Object (DAO).
8. `@Query`: Defines a custom SQL query to retrieve data from the database.
9. `@Insert`: Marks a method as an insert operation.
10. `@Update`: Marks a method as an update operation.
11. `@Delete`: Marks a method as a delete operation.
12. `@Transaction`: Specifies that a method should be executed within a database transaction.
13. `@Database`: Defines a Room database.
14. `@TypeConverter`: Specifies a custom type converter for Room to convert non-primitive types.

These are some of the commonly used Room annotations. Each annotation serves a specific purpose in defining the structure and behavior of the database and its entities.