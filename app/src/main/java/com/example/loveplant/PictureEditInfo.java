package com.example.loveplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

public class PictureEditInfo extends AppCompatActivity {

    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_edit_info);

        picture= (ImageView) findViewById(R.id.edit_picture);

        getPicture();
    }

    private void getPicture() {
        //获取字符串
        SharedPreferences sPreferences = getSharedPreferences("Picture", MODE_PRIVATE);
        String imageBase64 = sPreferences.getString("cameraImage", "");
        //把字符串解码成Bitmap对象
        byte[] byte64 = Base64.decode(imageBase64, 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(byte64);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);
        //显示图片
        picture.setImageBitmap(bitmap);
    }

}
