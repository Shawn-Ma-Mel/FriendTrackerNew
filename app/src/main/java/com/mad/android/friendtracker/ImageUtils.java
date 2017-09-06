package com.mad.android.friendtracker;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;

/**
 * Created by shawn on 29/08/2017.
 */

public class ImageUtils {

    public final static int ACTIVITY_RESULT_CAMERA = 0001;//选择 拍照 的返回码
    public final static int ACTIVITY_RESULT_ALBUM = 0002;//选择 相册 的返回码

    public Uri photoUri;// 图片路径的URI
    private Uri tempUri;

    public File picFile;// 图片文件

    private Context context;

    // 构造方法
    public ImageUtils(Context context) {
        super();
        this.context = context;
    }

    // 选择拍照的方式
    public void byCamera() {
        try {
            // 创建文件夹
            File uploadFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/icon");

            if (!uploadFileDir.exists()) {
                uploadFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            picFile = new File(uploadFileDir,
                    SystemClock.currentThreadTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

            // 启动Camera的Intent，传入图片路径作为存储路径
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            //启动Intent
            ((MainActivity) context).startActivityForResult(cameraIntent,
                    ACTIVITY_RESULT_CAMERA);

            System.out.println("-->tempUri : " + tempUri.toString()
                    + "-->path:" + tempUri.getPath());
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 选择相册的方式
    public void byAlbum() {
        try {
            // 创建文件夹
            File pictureFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/icon");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            picFile = new File(pictureFileDir,
                    SystemClock.currentThreadTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
