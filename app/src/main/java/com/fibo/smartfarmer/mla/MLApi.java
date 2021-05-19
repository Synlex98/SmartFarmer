package com.fibo.smartfarmer.mla;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fibo.smartfarmer.listeners.UploadListener;
import com.fibo.smartfarmer.models.Result;
import com.fibo.smartfarmer.utils.Constants;
import com.fibo.smartfarmer.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import timber.log.Timber;

public class MLApi {

    public void uploadAnImage(File theImage, UploadListener listener){
        AndroidNetworking.upload(Urls.MACHINE_LEARNING_URL)
                .addMultipartFile(Constants.IMAGE_KEY,theImage)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Timber.e(response.toString());
                        try {
                            String status=response.getString(Constants.ML_PREDICTED);
                            String disease=response.getString(Constants.ML_DISEASE);
                            String accuracy=response.getString(Constants.ML_ACCURACY);
                            listener.onData(new Result(status,disease,accuracy));

                        }catch (JSONException e){
listener.onFailure(e.getMessage());
                        }
                  }

                    @Override
                    public void onError(ANError anError) {
listener.onFailure(anError.getMessage());
                    }
                });

    }
}
