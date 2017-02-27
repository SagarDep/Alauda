package com.gmail.julianrosser91.alauda.presenter;

import android.os.Bundle;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.Constants;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.SetDetailModel;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetDetailInterface;

public class SetDetailPresenter implements SetDetailInterface.Presenter {

    private SetDetailInterface.View view;
    private SetDetailInterface.Model model;
    private Set set;
    private String setUid;
    private Bundle bundle;

    public SetDetailPresenter(SetDetailInterface.View view, Bundle extras) {
        this.view = view;
        this.bundle = extras;
        this.model = new SetDetailModel(this);
        getUidFromBundle();
    }

    private void getUidFromBundle() {
        if (bundle != null) {
            setUid = bundle.getString(Constants.BUNDLE_UID);
            if (setUid != null) {
                loadSetData(setUid);
            } else {
                view.setMessage(Alauda.getInstance().getString(R.string.message_error_bundle_no_uid));
            }
        } else {
            view.setMessage(Alauda.getInstance().getString(R.string.message_error_bundle_null));
        }
    }

    /*
     * View interface methods
     */

    @Override
    public void reattachView(SetDetailInterface.View view) {
        this.view = view;
        if (set != null) {
            view.setData(set);
        }
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    /*
     * Model interface methods
     */

    private void loadSetData(String setUid) {
        model.getSetFromUid(setUid);
    }

    @Override
    public void onDataRetrieved(Set set) {
        this.set = set;
        view.setData(set);
    }

    @Override
    public void onDataFailure(String message) {
        view.setMessage(message);
    }
}