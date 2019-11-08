package com.example.loveplant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddFragment extends Fragment {

    View v;
    ImageView img;
    ImageView capturePicture;

    private static final int Image_Capture_Code = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.add_fragment,container,false);

        img = v.findViewById(R.id.imageView);
        capturePicture = v.findViewById(R.id.capturePicture);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here

                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                capturePicture.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }


}
