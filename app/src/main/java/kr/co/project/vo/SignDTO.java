package kr.co.project.vo;

public class SignDTO {

    private String phone;
    private String certificationNumber;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public String getPhone() {
        return phone;
    }
}
