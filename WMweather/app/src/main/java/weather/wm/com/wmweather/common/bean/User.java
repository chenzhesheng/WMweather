package weather.wm.com.wmweather.common.bean;

import java.io.Serializable;

/**
 * Created by ChenZheSheng on 2017/4/9.
 */

public class User implements Serializable{
    private String uid;
    private String birthday;
    private String mobileno;
    private String gender;
    private String email;
    private String fullName;
    private String pro;
    private String city;
    private String avatar;
    private String userName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", pro='" + pro + '\'' +
                ", city='" + city + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
