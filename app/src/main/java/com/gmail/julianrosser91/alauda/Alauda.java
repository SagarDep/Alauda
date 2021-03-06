package com.gmail.julianrosser91.alauda;

import android.app.Application;

import com.gmail.julianrosser91.alauda.data.api.ApiInterface;
import com.gmail.julianrosser91.alauda.data.model.RealmString;
import com.gmail.julianrosser91.alauda.data.model.RealmStringListTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Alauda extends Application {

    private static Alauda sInstance;
    private static ApiInterface sApiInterface;

    public static Alauda getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Realm.init(this);
    }

    public synchronized ApiInterface getVodInterfaceAPI() {
        if (sApiInterface == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
                            }.getType(),
                            RealmStringListTypeAdapter.INSTANCE)
                    .create();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(Constants.READ_TIMOUT_SECONDS, TimeUnit.SECONDS)
                    .connectTimeout(Constants.CONNECTION_TIMOUT_SECONDS, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE)
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            sApiInterface = retrofit.create(ApiInterface.class);
        }
        return sApiInterface;
    }

}
