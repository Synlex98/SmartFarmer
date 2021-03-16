package com.fibo.smartfarmer.auth;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.models.User;
import com.fibo.smartfarmer.utils.Urls;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;

public class Authenticator {
    public static User user;
    private String OTP;
    private PhoneAuthOptions options=null;

    public String generateEmail(){
        String[] values=new String[]{"a","b","c","d","e","f","g","h","i","j"};

        HashMap<Integer,String> emailMaps=new HashMap<>();

        int counter=0;
        for (String value:values){
            emailMaps.put(counter,value);
            counter++;
        }

       // String phone=String.valueOf(user.getPhoneNumber());
        String phone="0751589067";
        StringBuilder builder=new StringBuilder();

        for (int i=0;i<phone.length();i++){
            String numString=String.valueOf(phone.charAt(i));
            int number=Integer.parseInt(numString);
            builder.append(emailMaps.get(number));
        }
        return builder.append("@smartfarmer.com").toString();
    }

    public void registerFarmer(Context context, CompleteListener listener){

String data= FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+user.getUserName()+"/"+user.getLocation();
String url= Urls.REGISTER_USER_URL+data;

Thread thread=new Thread(new Runnable() {
    @Override
    public void run() {
        AndroidNetworking.post(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            listener.onComplete();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
listener.onError(anError.getMessage());
                    }
                });
    }
});
thread.start();
    }

}
