package com.edu.edusocialnetwork;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.biometrics.BiometricManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareTab extends Fragment implements View.OnClickListener {

    // UI Components
    private ImageView imgHolder;
    private EditText edtDescription;
    private Button btnShare;

    Bitmap receivedImageBitmap;

    public ShareTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_tab, container, false);

        imgHolder = view.findViewById(R.id.imgHolder);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnShare = view.findViewById(R.id.btnShare);

        imgHolder.setOnClickListener(ShareTab.this);
        btnShare.setOnClickListener(ShareTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgHolder:

                // Condition for checking the permissions were granted
                if (Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                } else {
                    getImageForShare(); // Have to implement this method !!!
                }
                break;

            case R.id.btnShare:

                if (receivedImageBitmap != null) {
                    if (edtDescription.getText().toString().equals("")) {
                        FancyToast.makeText(getContext(),
                                "You have to add description",
                                Toast.LENGTH_LONG,
                                FancyToast.ERROR,
                                false).show();
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png", bytes);

                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture", parseFile);
                        parseObject.put("image_des", edtDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(getContext(),
                                            "Image was uploaded",
                                            Toast.LENGTH_LONG,
                                            FancyToast.SUCCESS,
                                            false).show();
                                } else {
                                    FancyToast.makeText(getContext(),
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG,
                                            FancyToast.ERROR,
                                            false).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                } else {
                    FancyToast.makeText(getContext(),
                            "You have to pick image",
                            Toast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false).show();;
                }
                break;
        }
    }

    private void getImageForShare() {
        /*
        FancyToast.makeText(getContext(),
                "Now you can pick the image...",
                Toast.LENGTH_LONG,
                FancyToast.SUCCESS,
                true).show();

        */

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    // Method for checking the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                getImageForShare();
            }
        }
    }

    // ??? I don't really now how to use this method
    // ??? Just trying to find some complete examples of the implementation
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    cursor.close();

                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                    imgHolder.setImageBitmap(receivedImageBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
