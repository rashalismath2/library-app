package com.ismathlifehacks.library.DBLite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ismathlifehacks.library.DAO.UserDAO;
import com.ismathlifehacks.library.Entity.User;

@Database(entities = {User.class},version = 1)
public abstract class Lite extends RoomDatabase {

    private static Lite instance;
    public abstract UserDAO userDAO();

    public static  synchronized Lite getInstance(Context context){

        if(instance==null){
            instance=Room.databaseBuilder(context.getApplicationContext(),Lite.class, "DBLite")
                    .build();
        }
        return instance;

    }

}
