package kr.co.project.vo;

import com.google.gson.annotations.SerializedName;

public class PublicData {

    @SerializedName("detailAddr")
    private String detailAddr;

    @SerializedName("emailAddr")
    private String emailAddr;

    @SerializedName("zipCd")
    private String zipCd;
    @SerializedName("zipAddr")
    private String zipAddr;

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public void setZipAddr(String zipAddr) {
        this.zipAddr = zipAddr;
    }

    public String getZipCd() {
        return zipCd;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public String getZipAddr() {
        return zipAddr;
    }
}
