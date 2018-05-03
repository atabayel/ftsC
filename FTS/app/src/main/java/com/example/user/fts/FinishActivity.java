package com.example.user.fts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.apache.commons.io.FileUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FinishActivity extends AppCompatActivity {

    private String lang, pageCount;
    private ArrayList<String> imgPaths = new ArrayList<>();
    ArrayList<String> docPaths = new ArrayList<>();
    private ArrayList<ImageView> images = new ArrayList<>();
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
    LinearLayout llImages;
    LinearLayout.LayoutParams lParams;
    ConstraintLayout constraintLayout;
    LinearLayout llMain;
    private ProgressBar progressBar;
    private TextView tvPgBar;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        lang = getIntent().getStringExtra("lang");
        pageCount = getIntent().getStringExtra("pageCount");
        imgPaths = getIntent().getStringArrayListExtra("imgs");
        docPaths = getIntent().getStringArrayListExtra("docs");
        constraintLayout = findViewById(R.id.constraintLayout);
        llMain = findViewById(R.id.llMain);
        llImages = findViewById(R.id.llImages);
        lParams = new LinearLayout.LayoutParams(matchParent, wrapContent);
        lParams.setMargins(8, 8, 8, 8);
        lParams.width = 500;
        lParams.height = 500;
        lParams.gravity = Gravity.LEFT;
        progressBar = findViewById(R.id.pgBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        tvPgBar = findViewById(R.id.tvPgBar);
        tvPgBar.setVisibility(TextView.INVISIBLE);


        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < imgPaths.size() - 1; i++) {
                    if (view.getId() == images.get(i).getId()) {
                        Intent intent = new Intent(FinishActivity.this, FullImageViewActivity.class);
                        intent.putExtra("uri", imgPaths.get(i));
                        startActivity(intent);
                    }
                }
            }
        };

        for (int i = 0; i < imgPaths.size(); i++) {
            try {
                images.add(new ImageView(this));
                images.get(i).setId(3000 + i);
                images.get(i).setOnClickListener(oclBtn);
                llImages.addView(images.get(i), lParams);
                Picasso.with(this)
                        .load("file://" + imgPaths.get(i))
                        .error(R.drawable.ic_error_outline_white_24dp)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        //.resize(500, 500)
                        .into(images.get(i));
            } catch (Exception e) {
                Log.d(MyContants.TAG, "error: "+e.getMessage());
            }
        }
        int j = 0;
        int resId = R.drawable.ic_unknown;
        int docsIndex = imgPaths.size() + docPaths.size();
        for (int i = imgPaths.size(); i < docsIndex; i++) {
            images.add(new ImageView(this));
            images.get(i).setId(3000 + i);
            images.get(i).setOnClickListener(oclBtn);
            llImages.addView(images.get(i), lParams);
            switch (docPaths.get(j).substring(docPaths.get(j).lastIndexOf(".") + 1)) {
                case "ppt":
                    resId = R.drawable.ic_ppt;
                    break;
                case "pptx":
                    resId = R.drawable.ic_ppt;
                    break;
                case "xls":
                    resId = R.drawable.ic_xls;
                    break;
                case "xlsx":
                    resId = R.drawable.ic_xls;
                    break;
                case "doc":
                    resId = R.drawable.ic_doc;
                    break;
                case "docx":
                    resId = R.drawable.ic_doc;
                    break;
                case "pdf":
                    resId = R.drawable.ic_pdf;
                    break;
                case "txt":
                    resId = R.drawable.ic_txt;
                    break;
                case "zip":
                    resId = R.drawable.ic_zip;
                    break;
                case "rar":
                    resId = R.drawable.ic_rar;
                    break;
            }
            Picasso.with(this)
                    .load(resId)
                    .error(R.drawable.ic_error_outline_white_24dp)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    //.resize(500, 500)
                    .into(images.get(i));
            j++;
        }
        TextView tvLang = findViewById(R.id.tvLang);
        TextView tvPageCount = findViewById(R.id.tvPageCount);
        tvLang.setText(getIntent().getStringExtra("lang"));
        tvPageCount.setText(getIntent().getStringExtra("pageCount"));
    }

    public void onClick(View view) {
        if (MyMethods.isNetworkOnline(this)) {
            sendingData();
        } else {
            Toast.makeText(this, getResources().getString(R.string.inet_off), Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

//    @NonNull
//    public static MultipartBody.Part prepareFilePart(Context context, String partName, File file) {
//        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
//        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
//    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String filePath) {
        File file = FileUtils.getFile(String.valueOf(filePath));
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(mimeType),
                        file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void sendingData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        tvPgBar.setVisibility(TextView.VISIBLE);
        MyPreferences preferences;
        preferences = new MyPreferences(this, "tablevar");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MyContants.BASE_URL) // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create(gson)) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .build();
        API api = retrofit.create(API.class);

        List<MultipartBody.Part> files = new ArrayList<>();

        String tmpNum = "";
        for (int i=0; i<imgPaths.size(); i++) {
//            File file = new File(imgPaths.get(i));
            tmpNum = String.valueOf(i);
            MultipartBody.Part body = prepareFilePart("files", imgPaths.get(i));
            //.concat(tmpNum)
            files.add(body);
        }


        RequestBody myid = createPartFromString(preferences.getVariable(MyContants.DB_TABLE_MY_ID));
        RequestBody language = createPartFromString(lang);
        RequestBody page_count = createPartFromString(pageCount);
        RequestBody urgency = createPartFromString("3");

        Call<ServerResponse> call = api.newOrder(
                myid,
                language,
                page_count,
                urgency,
                files);
        try {
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        tvPgBar.setVisibility(TextView.INVISIBLE);
                        if (response.body().getResponse().equals("ferror")) {
                            showToast(getResources().getString(R.string.field_err));
                        } else if (response.body().getResponse().equals("unknown_cl")) {
                            showToast(getResources().getString(R.string.unknown_client));
                        } else if (response.body().getResponse().equals("ord_added")) {
                            //Запрос отправился
                            newOrder(response.body().getId());
                        }
                    } else {
                        Log.d(MyContants.TAG, "failure response is: " + response.raw().toString());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.d(MyContants.TAG, "Failure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void newOrder(String order_id) {
        MyPreferences preferences = new MyPreferences(this, "tablevar");
        preferences.setVariable(MyContants.DB_TABLE_ORDER_ID, order_id);
        preferences.setVariable(MyContants.DB_TABLE_IS_ORDERED, "1");
        preferences.setVariable(MyContants.DB_TABLE_JUST_ORDERED, "1");
        Intent intent = new Intent(this, OrderAcceptedActivity.class);
        startActivity(intent);
        finish();
    }
}
