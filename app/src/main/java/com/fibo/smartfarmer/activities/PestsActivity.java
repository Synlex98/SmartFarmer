package com.fibo.smartfarmer.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.ai.InferenceEngine;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.db.PreferenceManager;
import com.fibo.smartfarmer.helpers.DownloadHelper;
import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.listeners.DownloadListener;
import com.fibo.smartfarmer.listeners.ExpertListener;
import com.fibo.smartfarmer.listeners.UploadListener;
import com.fibo.smartfarmer.mla.ImageLabeling;
import com.fibo.smartfarmer.mla.MLApi;
import com.fibo.smartfarmer.models.Result;
import com.fibo.smartfarmer.utils.Commons;
import com.fibo.smartfarmer.utils.Constants;
import com.fibo.smartfarmer.utils.ImageCompressor;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import timber.log.Timber;

public class PestsActivity extends AppCompatActivity {

    private ImageButton imageButton;
    Uri imageUri,compressedUri;
    private FloatingActionButton floatingActionButton;
    private TextView resultView,diseaseView,accuracyView;
    private MaterialButton controlButton,moreButton;
    private ScrollView scrollView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pests);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageButton=findViewById(R.id.addImage);
        controlButton=findViewById(R.id.control);
        moreButton=findViewById(R.id.more);
        resultView=findViewById(R.id.statusValue);
        diseaseView=findViewById(R.id.diseaseValue);
        accuracyView=findViewById(R.id.accuracyValue);
        floatingActionButton=findViewById(R.id.done);
        scrollView=findViewById(R.id.scroller);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
new GligarPicker().requestCode(Constants.GALLERY_REQUEST_CODE)
        .withActivity(PestsActivity.this)
        .limit(1)
        .show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compressedUri==null){
                    new Commons().showToast(getApplicationContext(),"You need to select an Image first");
                    return;
                }
                new ImageLabeling()
                        .labelImage(getApplicationContext(), compressedUri, new CompleteListener() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onComplete(String message) {
if (message.equals(Constants.LABEL_PASSED)){
    uploadImageForTesting();
}
else {
    new Commons().showToast(getApplicationContext(),"The Image you picked does not seem to be a plant. Please try again");
}
                            }
                        });

            }
        });

        if (PreferenceManager.isBooleanValueTrue(this,Constants.IS_FIRST_LAUNCH)) {
            downLoadRequiredFiles();
        }

        getSupportActionBar().setTitle(new Authenticator().getCurrentUser(PestsActivity.this).getUserName());

    }

    public void downLoadRequiredFiles(){
        Dialog dialog=new Dialog(PestsActivity.this);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);
        dialog.show();
        new Commons().showToast(getApplicationContext(),"Downloading 1 of 2");
       DownloadHelper.downLoadDiseaseFile(new DownloadListener() {
           @Override
           public void onDownloadComplete() {
               new Commons().showToast(getApplicationContext(),"Downloading 2 of 2");
               DownloadHelper.downLoadFertilizerFile(new DownloadListener() {
                   @Override
                   public void onDownloadComplete() {
                       new Commons().showToast(getApplicationContext(),"Done :)");
                       if (dialog.isShowing())dialog.dismiss();
                       PreferenceManager.putBooleanValue(PestsActivity.this,Constants.IS_FIRST_LAUNCH,false);
                   }
               });
           }
       });
    }

    private void uploadImageForTesting(){
        Dialog dialog=new Dialog(PestsActivity.this);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);
        dialog.show();

        new MLApi().uploadAnImage(new File(compressedUri.getPath()), new UploadListener() {
            @Override
            public void onData(Result result) {
                if (dialog.isShowing())dialog.dismiss();
scrollView.setVisibility(View.VISIBLE);
resultView.setText(result.getStatus());
diseaseView.setText(result.getDisease());
if (result.getDisease().equals(Constants.ML_HEALTHY_IMAGE)){
   diseaseView.setText("Seems Okay :)");

}
accuracyView.setText((int)Double.parseDouble(result.getAccuracy())+"%");

controlButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (!result.getStatus().equals(Constants.ML_HEALTHY_IMAGE)){
        showControlFor(result.getStatus());
            Timber.e("Showing Control");
        }
    }
});
            }
        });
    }

    private void showControlFor(String status) {
        Dialog tipsDialog=new Dialog(PestsActivity.this);
        tipsDialog.setContentView(R.layout.tips_view);
        TextView titleView=tipsDialog.findViewById(R.id.title);
        TextView contentView=tipsDialog.findViewById(R.id.content);
        MaterialButton closeButton=tipsDialog.findViewById(R.id.close);
        titleView.setText(String.format(Locale.getDefault(),"Control For %s",status));

        Timber.e(status);

        new InferenceEngine().inferDiseaseControl(PestsActivity.this, status, new ExpertListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void oControl(List<String> controlList) {
contentView.setText(joinList(controlList));
closeButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (tipsDialog.isShowing())tipsDialog.dismiss();
    }
});
tipsDialog.show();
            }
        });

    }

    public String joinList(List<String> stringList){
        Timber.e(String.valueOf(stringList.size()));
        int i=0;
        StringBuilder builder=new StringBuilder();
        for (String s:stringList){
            builder.append(i).append(". ").append(s).append('\n');
        }
return builder.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!=AppCompatActivity.RESULT_OK){
            return;
        }
        if (requestCode == Constants.GALLERY_REQUEST_CODE && data!=null && data.getExtras()!=null) {
            String[] paths = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            imageUri= Uri.fromFile(new File(Objects.requireNonNull(paths)[0]));
            new ImageCompressor().CompressImage(PestsActivity.this, imageUri, newUri -> runOnUiThread(() -> {
                imageButton.setImageURI(newUri);
                floatingActionButton.setVisibility(View.VISIBLE);
                compressedUri=newUri;
            }));


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId=item.getItemId();

        if (menuId==R.id.chatMenu){
            startActivity(new Intent(PestsActivity.this,ChatActivity.class));
        }
        
        if (menuId==R.id.logoutMenu){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(PestsActivity.this,LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}