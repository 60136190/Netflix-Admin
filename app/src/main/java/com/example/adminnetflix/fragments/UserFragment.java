package com.example.adminnetflix.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.ListAdminActivity;
import com.example.adminnetflix.activities.ListUserActivity;

public class UserFragment extends Fragment {
    private ConstraintLayout ctListUser;
    private ConstraintLayout ctListAdmin;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user, container, false);
        initUi();

        ctListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itOpenListUser = new Intent(getContext(), ListUserActivity.class);
                startActivity(itOpenListUser);
            }
        });

        ctListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itOpenListAdmin = new Intent(getContext(), ListAdminActivity.class);
                startActivity(itOpenListAdmin);
            }
        });


        return view;
    }

    private void initUi() {
        ctListUser = view.findViewById(R.id.ct_user);
        ctListAdmin = view.findViewById(R.id.ct_admin);
    }
}