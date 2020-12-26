package com.example.assignment_1;
import android.graphics.drawable.Icon;

public class WeatherDetails {
    int Icon;
    String upperText;
    String lowerText; 

    public void setIcon(int Icon) {
        this.Icon = Icon;
    }

    public void setUpperText(String upperText) {
        this.upperText = upperText;
    }

    public void setLowerText(String lowerText) {
        this.lowerText = lowerText;
    }

    public int getIcon() {
        return Icon;
    }

    public String getUpperText() {
        return upperText;
    }

    public String getLowerText() {
        return lowerText;
    }

    public WeatherDetails(int Icon, String upperText, String lowerText) {
        this.Icon = Icon;
        this.upperText = upperText;
        this.lowerText = lowerText;
    }
}
