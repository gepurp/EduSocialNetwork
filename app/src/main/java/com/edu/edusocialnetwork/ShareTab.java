package com.edu.edusocialnetwork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareTab extends Fragment implements View.OnClickListener {

    // UI Components
    private ImageView imgHolder;
    private EditText edtDescription;
    private Button btnShare;

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

                break;
        }
    }

    private void getImageForShare() {
        // Here I'll put some code for picking the image from user storage...
    }
}
