package com.example.pathdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.security.Key;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-09-24 20:43
 * @depiction ：
 */
public class NameViewModel extends ViewModel {
    private SavedStateHandle mState;
    public static final String KEY = "num";

    public NameViewModel(SavedStateHandle savedStateHandle) {
        this.mState = savedStateHandle;

    }


    public MutableLiveData<Integer> getNumber() {
        if (!mState.contains(KEY))
            mState.set(KEY, 0);
        return mState.getLiveData(KEY);
    }

// Rest of the ViewModel...

    public void add() {
        getNumber().setValue(getNumber().getValue() + 2);
    }

}
