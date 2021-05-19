package com.fibo.smartfarmer.ai;

import android.content.Context;

import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.listeners.FileListener;
import com.fibo.smartfarmer.models.Disease;
import com.fibo.smartfarmer.providers.FileProvider;
import com.fibo.smartfarmer.utils.Commons;
import com.fibo.smartfarmer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class KnowledgeBase {

    public synchronized void ParseDisease(Context context, CompleteListener completeListener){
        FileProvider.getInstance().readJSONFile(Constants.DISEASE_FILE, context, new FileListener() {
            @Override
            public void fileAvailable() {
Timber.e("File Available");
            }

            @Override
            public void onError(String message) {
                Timber.e(message);
                new Commons().showToast(context,message);
            }

            @Override
            public void onReadComplete(String content) {
                List<Disease> diseases=new ArrayList<>();
                Timber.e(content);

                if (content!=null){
                    try {
                        JSONArray array=new JSONArray(content);
                        for (int i=0; i<array.length();i++){
                            JSONObject object=array.optJSONObject(i);
                            String name=object.getString(Constants.NAME);
                            String controlMeasure=object.getString("control");
                            List<String> controls = new ArrayList<>(Arrays.asList(controlMeasure.split("__")));
                            diseases.add(new Disease(name,controls));

                        }
                        completeListener.onComplete(diseases);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
completeListener.onComplete(diseases);

            }
        });

    }

}
