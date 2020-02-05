package com.ismathlifehacks.library.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.Repository.UserRepo;

public class UserViewModel extends AndroidViewModel {

    private UserRepo userRepo;
    private User user;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepo=new UserRepo(application);
        user=userRepo.getUser();
    }

    public User getUser(){
        return this.user;
    }

    public void insert(User user){
        userRepo.insert(user);
    }
    public void delete(User user){
        userRepo.delete(user);
    }
    public void deleteAllUsers(){
        userRepo.deleteAllUsers();
    }
}
