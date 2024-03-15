package com.example.gezirehberiappteacher;
import java.io.Serializable;
public class GeziRehberiBilgileri implements Serializable {
    String imageID;
    String yerAdi, ulkeAdi, sehirAdi, tarihce, hakkinda;
    String placeName, countryName, cityName, history, about;

    public GeziRehberiBilgileri(String imageID, String yerAdi, String ulkeAdi, String sehirAdi, String tarihce, String hakkinda, String placeName, String countryName, String cityName, String history, String about) {
        this.imageID = imageID;
        this.yerAdi = yerAdi;
        this.ulkeAdi = ulkeAdi;
        this.sehirAdi = sehirAdi;
        this.tarihce = tarihce;
        this.hakkinda = hakkinda;
        this.placeName = placeName;
        this.countryName = countryName;
        this.cityName = cityName;
        this.history = history;
        this.about = about;
    }
}
