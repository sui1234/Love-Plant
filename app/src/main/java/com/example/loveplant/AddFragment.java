package com.example.loveplant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import com.squareup.otto.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AddFragment extends Fragment {

    View v;
    ImageView cameraIcon;
    ImageView capturePicture;
    TextView textView;
    TextView name;
    TextView days;
    EditText nameEdit;
    EditText daysEdit;
    Button save;

    private String mFilePath;
    private Bitmap bitmap;

    private static final int Image_Capture_Code = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.add_fragment,container,false);



        name = v.findViewById(R.id.name);
        days = v.findViewById(R.id.days);
        nameEdit = v.findViewById(R.id.nameEdit);
        daysEdit = v.findViewById(R.id.daysEdit);
        save = v.findViewById(R.id.saveButton);

        mFilePath = Environment.getExternalStorageDirectory().getPath();// get sd directory
        mFilePath = mFilePath + "/" + "flower.png";
        Log.d("sui",mFilePath);

        cameraIcon = v.findViewById(R.id.cameraIcon);
        capturePicture = v.findViewById(R.id.capturePicture);
        textView = v.findViewById(R.id.textView);



        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

               // Uri photoUri = Uri.fromFile(new File(mFilePath)); // 传递路径  
                //Uri photoUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider",new File(mFilePath));

                //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径 
                //Log.d("sui photouri",photoUri.toString());

                startActivityForResult(intent, Image_Capture_Code);
                //getActivity().onBackPressed();





            }

        });

        return v;

    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sui resultcode",String.valueOf(resultCode));
            Log.d("sui data",String.valueOf(data));


        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                capturePicture.setImageBitmap(bp);
                cameraIcon.setVisibility(getView().GONE);
                textView.setVisibility(getView().GONE);

                name.setVisibility(View.VISIBLE);
                days.setVisibility(View.VISIBLE);
                nameEdit.setVisibility(View.VISIBLE);
                daysEdit.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }

        /*Log.d("sui requestcode", String.valueOf(requestCode));

        if (resultCode == RESULT_OK) {
           Log.d("sui resultCode","");
            if (requestCode == Image_Capture_Code) {
                FileInputStream fis = null;

                try {
                    Log.d("sui try", "trying");

                    fis = new FileInputStream(mFilePath); // 根据路径获取数据
                    Log.d("sui", "fis" + fis.toString());
                    bitmap = BitmapFactory.decodeStream(fis);    //获取图片

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intentBitmap();
                        }
                    }).start();

                    Intent intent = new Intent(getActivity(), PictureEditInfo.class);
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {

                        if (fis != null)
                        fis.close();// 关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }*/

    }

    private boolean hasSDCard() {
        // 获取外部存储的状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有SD卡
            return true;
        }
        return false;
    }





    private void intentBitmap() {
        //把Bitmap转码成字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50,baos);
        String imageBase64 = new String (Base64.encode(baos.toByteArray(), 0));
        //把字符串存到SharedPreferences里面
        SharedPreferences prePicture = getContext().getSharedPreferences("Picture", MODE_PRIVATE);
        SharedPreferences.Editor editor = prePicture.edit();
        editor.putString("cameraImage", imageBase64);
        editor.commit();

        Log.d("sui","bitmap to string");
    }


}
