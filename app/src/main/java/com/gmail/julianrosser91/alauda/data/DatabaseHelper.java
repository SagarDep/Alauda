package com.gmail.julianrosser91.alauda.data;

import android.content.Intent;
import android.net.Uri;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.Constants;
import com.gmail.julianrosser91.alauda.data.model.Set;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DatabaseHelper {

    public static Set getSetFromUid(String uid) {
        Realm realm = getRealmDatabase();
        return realm.where(Set.class)
                .equalTo(Constants.SET_UID_KEY, uid)
                .findFirst();
    }

    public static RealmResults<Set> getAllSets() {
        Realm realmDatabase = getRealmDatabase();
        return realmDatabase.where(Set.class).findAll();
    }

    public static void saveAllSets(ArrayList<Set> data) {
        Realm realmDatabase = getRealmDatabase();
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealmOrUpdate(data);
        realmDatabase.commitTransaction();
    }

    public static void updateSetObject(Set set) {
        Realm realmDatabase = getRealmDatabase();
        realmDatabase.beginTransaction();
        realmDatabase.insertOrUpdate(set);
        realmDatabase.commitTransaction();
    }

    /*
     *  Use this to export Realm DB so we can view it with Realm Browser. Copied from here -
     *  http://stackoverflow.com/questions/28478987/how-to-view-my-realm-file-in-the-realm-browser
     */
    public static Intent getExportDatabaseIntent() {

        // init realm
        Realm realm = Realm.getDefaultInstance();

        File exportRealmFile = null;
        // get or create an "export.realm" file
        exportRealmFile = new File(Alauda.getInstance().getExternalCacheDir(), "export.realm");

        // if "export.realm" already exists, delete
        exportRealmFile.delete();

        // copy current realm to "export.realm"
        realm.writeCopyTo(exportRealmFile);

        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "EMAIL");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Realm Database");
        intent.putExtra(Intent.EXTRA_TEXT, " ");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        return intent;
    }

    private static Realm getRealmDatabase() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }
}
