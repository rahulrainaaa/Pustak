package com.product.pustak.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to hold data related to post.
 */
public class Post implements Parcelable {

    public final static Parcelable.Creator<Post> CREATOR = new Creator<Post>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return (new Post[size]);
        }

    };
    private String topic;
    private String name;
    private String author;
    private String pub;
    private String type;
    private String edition;
    private String desc;
    private String sub;
    private String avail;
    private Float mrp;
    private Integer cond;
    private Float rent;
    private Float price;
    private Integer days;
    private String mobile;
    private Boolean active;
    private String date;
    private String expiry;

    protected Post(Parcel in) {
        this.topic = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.pub = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.edition = ((String) in.readValue((String.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
        this.sub = ((String) in.readValue((String.class.getClassLoader())));
        this.avail = ((String) in.readValue((String.class.getClassLoader())));
        this.mrp = ((Float) in.readValue((Float.class.getClassLoader())));
        this.cond = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rent = ((Float) in.readValue((Float.class.getClassLoader())));
        this.price = ((Float) in.readValue((Float.class.getClassLoader())));
        this.days = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.expiry = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Post() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public Float getMrp() {
        return mrp;
    }

    public void setMrp(Float mrp) {
        this.mrp = mrp;
    }

    public Integer getCond() {
        return cond;
    }

    public void setCond(Integer cond) {
        this.cond = cond;
    }

    public Float getRent() {
        return rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(topic);
        dest.writeValue(name);
        dest.writeValue(author);
        dest.writeValue(pub);
        dest.writeValue(type);
        dest.writeValue(edition);
        dest.writeValue(desc);
        dest.writeValue(sub);
        dest.writeValue(avail);
        dest.writeValue(mrp);
        dest.writeValue(cond);
        dest.writeValue(rent);
        dest.writeValue(price);
        dest.writeValue(days);
        dest.writeValue(mobile);
        dest.writeValue(active);
        dest.writeValue(date);
        dest.writeValue(expiry);
    }

    public int describeContents() {
        return 0;
    }

}