package com.example.password_manager_app.data

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class User(
    @PrimaryKey()
    val id: String = "",

    @ColumnInfo
    val name: String = "",

    @ColumnInfo
    val email: String = "",

    @ColumnInfo
    val username: String = "",

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo
    val token: String = "",
) {}

@Dao
interface UserDao {
    @Query("SELECT token from User where id = :userId")
    suspend fun getToken(userId: String): String?

    @Query("SELECT * from User where id = :userId")
    suspend fun getUser(userId: String): User?

    @Insert()
    suspend fun insertUser(user: User)
}

class RegisterForm(
    var name: String = "",
    var email: String = "",
    var username: String = "",
    var password: String = "",

    @Expose(serialize = false)
    var confirmPassword: String = ""
) {}

class LoginForm(
    val email: String = "",
    val password: String = ""
)

data class AuthResponse(
    val user: User,
    val token: String
)