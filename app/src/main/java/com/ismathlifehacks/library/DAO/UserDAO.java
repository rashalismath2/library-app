package com.ismathlifehacks.library.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ismathlifehacks.library.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("select * from tbl_user limit 1")
    User getUser();

    @Query("delete from tbl_user")
    void deleteAllUsers();


}
