package com.example.magiccoffee_v2.DataLocal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.magiccoffee_v2.GUI.BarcodeActivity;

import java.io.FileOutputStream;
import java.io.InputStream;
//SaveImageInternalStorage
public class DownloadImageUrl extends AsyncTask<String, String, Bitmap> {
    private Context mContext;
    public DownloadImageUrl(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Bitmap doInBackground(String... URL) {
        String imageURL = URL[0];
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        ImageInternalStorage.saveImage(mContext, result, "no_image.jpg");
    }
}
