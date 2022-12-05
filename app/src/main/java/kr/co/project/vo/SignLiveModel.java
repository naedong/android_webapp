package kr.co.project.vo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

public class SignLiveModel extends ViewModel {
    private final MutableLiveData<MembSign> membSignMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> userId = new MutableLiveData<>();
    private final MutableLiveData<String> userPwd = new MutableLiveData<>();
    private final MutableLiveData<String> mobile = new MutableLiveData<>();
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> zip = new MutableLiveData<>();
    private final MutableLiveData<String> zipaddr = new MutableLiveData<>();
    private final MutableLiveData<String> detaild = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> getDetaild() {
        return detaild;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getMobile() {
        return mobile;
    }

    public MutableLiveData<String> getUserId() {
        return userId;
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getUserPwd() {
        return userPwd;
    }

    public MutableLiveData<String> getZip() {
        return zip;
    }

    public MutableLiveData<String> getZipaddr() {
        return zipaddr;
    }


    public SignLiveModel( ){
        membSignMutableLiveData.setValue(null);
    }

    public void setMembSignMutableLiveData(MembSign membSign){
         membSignMutableLiveData.setValue(membSign);
    }

    public MutableLiveData<MembSign> getMembSignMutableLiveData() {
        return membSignMutableLiveData;
    }
}
