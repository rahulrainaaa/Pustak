
package com.product.pustak.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

    private String name;    // title of book
    private String author;  // book author
    private String pub;     // publication
    private String type;    // magzine, textbook, referencebook, novel,
    private String edition; // edition of book
    private String desc;    // description
    private String sub;     // subject
    private String avail;   // sell, rent
    private Float mrp;      // MRP marked for new book.
    private Integer cond;   // Book condition 1 to 5.
    private Float rent;     // rent per day.
    private Float price;    // Second-hand sale.
    private Integer days;   // Max number of days to rent.
    private Boolean active; // true = visible/active, false = invisible/inactive.

    public final static Creator<Post> CREATOR = new Creator<Post>() {


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

    protected Post(Parcel in) {
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
        this.active = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Post() {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeValue(active);
    }

    public int describeContents() {
        return 0;
    }

}
