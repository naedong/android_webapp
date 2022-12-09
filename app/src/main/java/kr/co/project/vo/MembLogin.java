package kr.co.project.vo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

public class MembLogin extends ViewModel {
    private  MutableLiveData<String> connectIp;



    public MutableLiveData<String> getConnectIp(){
        if(connectIp == null){
            connectIp = new MutableLiveData<>();
        }
        return connectIp;
    }

    public static class MembLogins  {

        @SerializedName("connectIp")
        private  String ip;
        private  Long membSn;
        private  String membId;
        private  String membPwd;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setMembId(String membId) {
            this.membId = membId;
        }

        public Long getMembSn() {
            return membSn;
        }

        public String getMembPwd() {
            return membPwd;
        }

        public void setMembSn(Long membSn) {
            this.membSn = membSn;
        }

        public String getMembId() {
            return membId;
        }

        public void setMembPwd(String membPwd) {
            this.membPwd = membPwd;
        }

    }

}

