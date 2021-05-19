package com.fibo.smartfarmer.mla;

import android.content.Context;
import android.net.Uri;

import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

public class ImageLabeling {


    public void labelImage(Context context, Uri imageUri, CompleteListener listener){
        InputImage inputImage;
        try {
            inputImage=InputImage.fromFilePath(context,imageUri);

            ImageLabeler labeler= com.google.mlkit.vision.label.ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
            labeler.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                        @Override
                        public void onSuccess(List<ImageLabel> imageLabels) {
                            for (
                                    ImageLabel label : imageLabels) {
                                String labelText = label.getText();
                                if (labelText.equals("Plant")) {
                                    listener.onComplete(Constants.LABEL_PASSED);
                                    return;
                                }
                            }
                            listener.onComplete(Constants.LABEL_FAILED);
                        }
                    }).addOnFailureListener(e -> {
                        Timber.e(e);
                        listener.onError(e.getMessage());
                    });
        }catch (IOException e){
            listener.onError(e.getMessage());
        }

    }
}
