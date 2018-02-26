package com.product.pustak.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to hold data related to user profile.
 */
public class User implements Parcelable {

    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    private String name;        // User full name.
    private String state;       // State of country.
    private String city;        //City where living.
    private String area;        // Area or locality (nearby).
    private String geo;         // geo Lat-Lng for maps.
    private String country;     // Country.
    private String postal;      // Postal Code.
    private String pic;         // profile picture Image URL.
    private String mobile;      // Mobile number with country code.
    private Float rate;         // Average rating given to this user.
    private Integer rateCount;  // Total ratings given (count).
    private String email;       // EmailID of user for emailing.
    private String work;        // Work, employment, designation.

    protected User(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.area = ((String) in.readValue((String.class.getClassLoader())));
        this.geo = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.postal = ((String) in.readValue((String.class.getClassLoader())));
        this.pic = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.rate = ((Float) in.readValue((Float.class.getClassLoader())));
        this.rateCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.work = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(@SuppressWarnings("SameParameterValue") String pic) {
        this.pic = pic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(@SuppressWarnings("SameParameterValue") Float rate) {
        this.rate = rate;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(state);
        dest.writeValue(city);
        dest.writeValue(area);
        dest.writeValue(geo);
        dest.writeValue(country);
        dest.writeValue(postal);
        dest.writeValue(pic);
        dest.writeValue(mobile);
        dest.writeValue(rate);
        dest.writeValue(rateCount);
        dest.writeValue(email);
        dest.writeValue(work);
    }

    public int describeContents() {
        return 0;
    }

}
