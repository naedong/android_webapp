package kr.co.project.vo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TokenModel extends ViewModel {
    private static String TAG = Maybe.class.getSimpleName();
    public final MutableLiveData<String> token = new MutableLiveData<>();




    public TokenModel(){
        token.setValue("");
        Log.i(TAG, "생성자 value 값"+token.getValue());
    }

}
