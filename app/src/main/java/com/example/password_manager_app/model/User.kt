package com.example.password_manager_app.data

import androidx.room.*
import com.google.gson.annotations.Expose

@Entity
data class User(
    @PrimaryKey
    val id: String = "",

    @ColumnInfo
    val name: String = "",

    @ColumnInfo
    val email: String = "",

    @ColumnInfo
    val username: String = "",

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo
    var token: String = "",
)

@Dao
interface UserDao {
    @Query("SELECT token from User where id = :userId")
    suspend fun getToken(userId: String): String?

    @Query("SELECT * from User LIMIT 1")
    suspend fun getUser(): User?

    @Insert
    suspend fun insertUser(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteUsers()

    @Update
    suspend fun updateUser(user: User)
}

class RegisterForm(
    var name: String = "",
    var email: String = "",
    var username: String = "",
    var password: String = "",

    @Expose(serialize = false)
    var confirmPassword: String = ""
)

class LoginForm(
    val email: String = "",
    val password: String = ""
)

class UpdateForm(
    val email: String? = "",
    val password: String = "",
    val new_password: String? = "",
    val token: String = "",
    val user_id: String = ""
)

data class AuthResponse(
    val user: User,
    val token: String
)