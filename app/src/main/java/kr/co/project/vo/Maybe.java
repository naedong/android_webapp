package kr.co.project.vo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class Maybe extends ViewModel {
    private static String TAG = Maybe.class.getSimpleName();
    public final MutableLiveData<Integer> value = new MutableLiveData<>();




    public Maybe(){
        value.setValue(0);

        Log.i(TAG, "생성자 value 값"+value.getValue().intValue());
    }

    public void increase(){
        value.setValue(value.getValue()+1);
        Log.i(TAG, "value 값"+value.getValue().intValue());
    }

    public void decrease(){
        value.setValue(value.getValue() - 1);
    }
}

