package com.edu.edusocialnetwork;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    // UI Components
    private EditText edtProfileName, edtBio, edtProfession, edtHobby, edtStatus;
    private Button btnUpdate;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobby = view.findViewById(R.id.edtHobby);
        edtStatus = view.findViewById(R.id.edtStatus);

        btnUpdate = view.findViewById(R.id.btnUpdate);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null) {
            edtProfileName.setText("");
        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileBio") == null) {
            edtBio.setText("");
        } else {
            edtBio.setText(parseUser.get("profileBio").toString());
        }

        if (parseUser.get("profileProfession") == null) {
            edtProfession.setText("");
        } else {
            edtProfession.setText(parseUser.get("profileProfession").toString());
        }

        if (parseUser.get("profileHobby") == null) {
            edtHobby.setText("");
        } else {
            edtHobby.setText(parseUser.get("profileHobby").toString());
        }

        if (parseUser.get("profileStatus") == null) {
            edtStatus.setText("");
        } else {
            edtStatus.setText(parseUser.get("profileStatus").toString());
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtBio.getText().toString());
                parseUser.put("profileProfession", edtProfession.getText().toString());
                parseUser.put("profileHobby", edtHobby.getText().toString());
                parseUser.put("profileStatus", edtStatus.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(),
                                    "Info Updated",
                                    Toast.LENGTH_LONG,
                                    FancyToast.INFO,
                                    true).show();
                        } else {
                            FancyToast.makeText(getContext(),
                                    "There was an error " + e.getMessage(),
                                    Toast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
