package com.android.example.myfoodapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LoginDao {

    @Insert(onConflict = REPLACE)
    void insert(LoginData loginData);

    @Query("SELECT * FROM LoginDetails WHERE email = :mail AND password = :pass")
    boolean isRowExist(String mail, String pass);

    @Query("SELECT * FROM LoginDetails WHERE email = :mail")
    boolean isEmailExist(String mail);

    @Query("SELECT userName FROM LoginDetails WHERE email = :mail")
    String getUserName(String mail);

    @Query("SELECT email FROM LoginDetails WHERE email = :mail")
    String getUserEmail(String mail);

    @Query("SELECT password FROM LoginDetails WHERE email = :mail")
    String getUserPassword(String mail);

    @Query("SELECT phoneNo FROM LoginDetails WHERE email = :mail")
    String getUserPhoneNo(String mail);

    @Query("SELECT address FROM LoginDetails WHERE email = :mail")
    String getUserAddress(String mail);

    @Query("SELECT orderHistoryCount FROM LoginDetails WHERE email = :mail")
    int getUserCount(String mail);

    @Query("UPDATE LoginDetails SET address = :add WHERE email = :mail")
    void setUserAddress(String add, String mail);

    @Query("UPDATE LoginDetails SET phoneNo = :num WHERE email = :mail")
    void setUserPhoneNo(String num, String mail);

    @Query("UPDATE LoginDetails SET orderHistoryCount = :num WHERE email = :mail")
    void setUserCount(int num, String mail);

    //Used in forgot activity
    @Query("UPDATE LoginDetails SET password = :pass WHERE email = :mail")
    void setPassword(String mail, String pass);
}
