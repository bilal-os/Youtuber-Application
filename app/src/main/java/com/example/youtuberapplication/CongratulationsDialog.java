package com.example.youtuberapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class CongratulationsDialog extends DialogFragment {
    private String userName;

    public CongratulationsDialog(String userName) {
        this.userName = userName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_dialog_congratulations, container, false);
        TextView nameText = view.findViewById(R.id.userNameText);
        nameText.setText(userName);

        Button signInBtn = view.findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(v -> {
            dismiss();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return view;
    }
}
