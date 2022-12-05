package kr.co.project.vo;

import com.google.gson.annotations.SerializedName;

public class MembSign {
    @SerializedName("membSn")
    private Long membSn;
    @SerializedName("membCls")
    private String membCls;
    @SerializedName("membStatusCd")
    private Integer membStatusCd;
    @SerializedName("membId")
    private String membId;
    @SerializedName("membPwd")
    private String membPwd;
    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("membNm")
    private String membNm;
    @SerializedName("publicData")
    private PublicData publicData;
    @SerializedName("lastLoginDtm")
    private String lastLoginDtm;


    public void setLastLoginDtm(String lastLoginDtm) {
        this.lastLoginDtm = lastLoginDtm;
    }

    public void setMembCls(String membCls) {
        this.membCls = membCls;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public void setMembNm(String membNm) {
        this.membNm = membNm;
    }

    public void setMembPwd(String membPwd) {
        this.membPwd = membPwd;
    }

    public void setMembSn(Long membSn) {
        this.membSn = membSn;
    }

    public void setMembStatusCd(Integer membStatusCd) {
        this.membStatusCd = membStatusCd;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setPublicData(PublicData publicData) {
        this.publicData = publicData;
    }

    public Integer getMembStatusCd() {
        return membStatusCd;
    }

    public Long getMembSn() {
        return membSn;
    }

    public PublicData getPublicData() {
        return publicData;
    }

    public String getLastLoginDtm() {
        return lastLoginDtm;
    }

    public String getMembCls() {
        return membCls;
    }

    public String getMembId() {
        return membId;
    }

    public String getMembNm() {
        return membNm;
    }

    public String getMembPwd() {
        return membPwd;
    }

    public String getMobileNo() {
        return mobileNo;
    }
}
