package com.example.dell.sae;

import android.util.Log;

import java.util.Random;

public class Utils {
    public static int getSubjectIcon(String subjectCode) {
        int resId = 0;
        switch (subjectCode) {
            case "UCS608":
                resId = R.drawable.ic_cloud_computing;
                break;
            case "UCS616":
                resId = R.drawable.ic_ads;
                break;
            case "UCS503":
                resId = R.drawable.ic_se;
                break;
            case "UCS507":
                resId = R.drawable.ic_ca;
                break;
            case "UCS521":
                resId = R.drawable.ic_ai;
                break;
            case "UML501":
                resId = R.drawable.ic_ml;
                break;
            default:
                resId = R.drawable.ic_open_book_1;
        }
        return resId;
    }

    public static int getRandomColor() {
        int colors[] = new int[]{R.color.lightBlue, R.color.pink, R.color.yellow, R.color.orange, R.color.green, R.color.red, R.color.violet};
        Random random = new Random();
        int max = colors.length - 1;
        int min = 0;
        return colors[random.nextInt((max - min) + 1) + min];
    }

    public static int getMarksColor(int marksObtained, int totalMarks) {
        double fraction = ((double) marksObtained) / ((double) totalMarks);
        fraction *= 100;
        int percent = (int) fraction;
        if (percent < 33) return R.color.red;
        if (percent < 65) return R.color.orange;
        return R.color.mat_green;
    }
}
