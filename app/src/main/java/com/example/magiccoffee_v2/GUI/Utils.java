package com.example.magiccoffee_v2.GUI;

import android.content.Context;

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

}
