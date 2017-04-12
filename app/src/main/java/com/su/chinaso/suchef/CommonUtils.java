package com.su.chinaso.suchef;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chinaso on 2017/3/17.
 */

class  CommonUtils {
    /**
     * 由string类型的路径加载图片
     *
     * @param context
     * @param path
     * @return
     */
    public static Bitmap getBitmap(Context context, String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > 1024 || o.outWidth > 1024) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(1024 / (double) Math.max(
                                o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            if (scale < 4) {
                scale = 4;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = context.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 将图片的uri转为绝对路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
