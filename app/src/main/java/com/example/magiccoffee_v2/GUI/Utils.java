package com.example.magiccoffee_v2.GUI;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static String FILE_UID = "Uid.txt";
    public static void writeData(String data, Context context, String filename)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String readData(Context context, String filename){
        String data = "";
        try
        {
            File file = context.getFileStreamPath(filename);
            if (file.exists()){
                FileInputStream fin = context.openFileInput(filename);
                int a;
                StringBuilder temp = new StringBuilder();
                while ((a = fin.read()) != -1)
                {
                    temp.append((char)a);
                }
                data = temp.toString();
                fin.close();
                return data;
            }
            else return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
