package kr.co.project.vo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddressData extends ViewModel {
    private static String TAG = AddressData.class.getSimpleName();
    public final MutableLiveData<Integer> zipCd = new MutableLiveData<>();
    public final MutableLiveData<String> fulladdress = new MutableLiveData<>();


    public AddressData(){
        zipCd.setValue(0);
        fulladdress.setValue("");
        Log.i(TAG, "생성자 value 값"+zipCd.getValue().intValue());
        Log.i(TAG, "생성자 value 값"+fulladdress.getValue());

    }

}
