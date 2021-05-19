package com.fibo.smartfarmer.ai;

import android.content.Context;

import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.listeners.ExpertListener;
import com.fibo.smartfarmer.models.Disease;

import java.util.List;

import timber.log.Timber;

public class InferenceEngine {
    public void inferDiseaseControl(Context context, String disease, ExpertListener listener) {
     new KnowledgeBase().ParseDisease(context, new CompleteListener() {
         @Override
         public void onComplete(List<Disease> diseaseList) {
             Timber.e("To Check: "+disease);
             for (Disease disease1:diseaseList){
                 Timber.e(disease1.getName());
                 if (disease1.getName().trim().equals(disease.trim())){
                     listener.oControl(disease1.getControlList());
                     return;
                 }
             }
         }
     });
    }
    
}
