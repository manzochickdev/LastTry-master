package xyz.manzodev.lasttry.Utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static FileUtils fileUtils;
    Context context;

    public FileUtils(Context context) {
        this.context = context;
    }

    public static FileUtils getInstance(Context context){
        if (fileUtils==null){
            fileUtils = new FileUtils(context);
        }
        return fileUtils;
    }

    public void saveBitmap(int id,Bitmap bitmap){
        String name = Integer.toString(id);
        File file = new File(context.getFilesDir(),name);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
