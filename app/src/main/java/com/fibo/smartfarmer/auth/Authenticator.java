package com.fibo.smartfarmer.auth;
import android.content.Context;
import android.content.Intent;

import com.fibo.smartfarmer.activities.LoginActivity;
import com.fibo.smartfarmer.models.User;
import com.fibo.smartfarmer.utils.Commons;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authenticator {
    public static User user;

    public User getCurrentUser(Context context){
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String userName=user.getDisplayName().split("__")[0];
            String userLocation = user.getDisplayName().split("__")[1];
            String phoneNumber = user.getDisplayName().split("__")[2];
            String email = user.getEmail();
            return User.getInstance()
                    .setUserName(userName)
                    .setLocation(userLocation)
                    .setPhoneNumber(phoneNumber)
                    .setUserEmail(email);
        }
      new Commons().showToast(context,"Your Session has expired");
        context.startActivity(new Intent(context, LoginActivity.class));
        return null;
    }

    public String getCurrentUserID(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public boolean isCurrentUser(String userId){
        return userId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public boolean isCurrentUserLoggedIn(){
        return FirebaseAuth.getInstance().getCurrentUser()!=null;
    }

}
