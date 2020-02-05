package com.ismathlifehacks.library.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.ismathlifehacks.library.DAO.UserDAO;
import com.ismathlifehacks.library.DBLite.Lite;
import com.ismathlifehacks.library.Entity.User;

public class UserRepo {
    private UserDAO userDAO;
    private User user;

    public User getUser() {
        return user;
    }


    public UserRepo(Application application) {
        Lite db=Lite.getInstance(application);
        userDAO=db.userDAO();
        user=userDAO.getUser();
    }

    public void  insert(User user){
        new InsertUserAsyncTask(userDAO).execute(user);
    }
    public void  delete(User user){
        new DeleteUserAsyncTask(userDAO).execute(user);
    }

    public void deleteAllUsers() {new DeleteAllUsersAsyncTask(userDAO).execute();   }

    private class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        public InsertUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.insert(users[0]);
            return null;
        }
    }

    private class DeleteUserAsyncTask  extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        public DeleteUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.delete(users[0]);
            return null;
        }
    }

    private class DeleteAllUsersAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDAO userDAO;

        public DeleteAllUsersAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            userDAO.deleteAllUsers();
            return null;
        }
    }
}
