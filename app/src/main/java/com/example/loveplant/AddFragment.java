package com.example.loveplant;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class AddFragment extends Fragment{

    private View v;
    private ImageView cameraIcon;
    private ImageView capturePicture;
    private TextView textView;
    private TextView name;
    private TextView days;
    private EditText nameEdit;
    private EditText daysEdit;
    private Button save;
    private String inputName;
    private String inputDays;

    private boolean theNameIsExisted;


    String timeStamp;
    //private String mFilePath;
    private Bitmap bitmap;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {

        v = inflater.inflate(R.layout.add_fragment,container,false);



        name = v.findViewById(R.id.name);
        days = v.findViewById(R.id.days);
        nameEdit = v.findViewById(R.id.nameEdit);
        daysEdit = v.findViewById(R.id.daysEdit);
        save = v.findViewById(R.id.saveButton);


        cameraIcon = v.findViewById(R.id.cameraIcon);
        capturePicture = v.findViewById(R.id.capturePicture);
        textView = v.findViewById(R.id.textView);


        cameraIcon.setOnClickListener((View.OnClickListener) this);
        save.setOnClickListener((View.OnClickListener) this);

        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();

            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.saveButton:

                        inputName= nameEdit.getText().toString();
                        inputDays = daysEdit.getText().toString();
                        theNameIsExisted();

                        if(theNameIsExisted == true){
                            Toast.makeText(getActivity(),"The name is already exist",Toast.LENGTH_SHORT).show();
                        }else{

                            if(inputName.length() == 0 || inputDays.length() == 0) {
                                Toast.makeText(getActivity(), "Can not be empty", Toast.LENGTH_SHORT).show();
                            }else {
                                //set user input data to PlantInfo
                                PlantInfo plantInfo = new PlantInfo();
                                plantInfo.setName(inputName);
                                plantInfo.setDay(inputDays);
                                plantInfo.setImageUri(currentPhotoPath);
                                plantInfo.setTimeStampe(timeStamp);
                                Log.d("sui plantInfo name", "is " + plantInfo.name);

                                //save the data to room database
                                MainActivity.myAppDatabase.myDao().addPlant(plantInfo);

                                Log.d("sui save data", "successfully");
                                Toast.makeText(getActivity(), "Plantinfo added succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent;
                                intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                            }

                        }
                        break;
                        default:
                            break;
                }
            }
        });
        return v;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sui resultcode",String.valueOf(resultCode));
        Log.d("sui data",String.valueOf(data));

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {

                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(currentPhotoPath));
                    capturePicture.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //bitmap = (Bitmap) data.getExtras().get("data");


                cameraIcon.setVisibility(getView().GONE);
                textView.setVisibility(getView().GONE);


                name.setVisibility(View.VISIBLE);
                days.setVisibility(View.VISIBLE);
                nameEdit.setVisibility(View.VISIBLE);
                daysEdit.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);



            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }

    }



    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        Log.d("Sui currentPhotoPath","is" + currentPhotoPath);
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;

            try {
                photoFile = createImageFile();
                Log.d("sui photoFile","is null and photoFile is created  " + photoFile);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.loveplant.provider",
                        photoFile);

                Log.d("sui photoFile","is not null and photoUri is " + photoURI );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    public boolean theNameIsExisted(){
        theNameIsExisted = false;
        int plantDbSize = MainActivity.myAppDatabase.myDao().getPlantInfo().size();
        for(int i = 0 ; i < plantDbSize; i++){
            PlantInfo plantInfo = MainActivity.myAppDatabase.myDao().getPlantInfo().get(i);
            if(plantInfo.getName().equals(inputName)){
                theNameIsExisted = true;
            }
        }
        return theNameIsExisted;
    }



    public String bitmapToString(Bitmap bitmap){
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }




}
