package com.example.dell.sae;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.sae.activities.EvaluationDetailActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static File writeFileToDisk(Context context, String fileName, InputStream fileStream) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName);
            if (file.exists()) return file;

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;

                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = fileStream.read(fileReader);
                    if (read == -1) break;
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName);
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (fileStream != null) fileStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public static void openFile(Context context, File file) {
        if (file == null) return;
        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, "com.example.dell.sae.GenericFileProvider", file);
        target.setDataAndType(uri, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF Reader installed!", Toast.LENGTH_SHORT).show();
        }
    }
}
