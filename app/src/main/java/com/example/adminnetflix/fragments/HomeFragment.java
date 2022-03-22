package com.example.adminnetflix.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.InformationAdminActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View view;
    private ImageView img_admin;
    private TextView tv_name_of_admin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUi();
        getProfile();

        img_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),InformationAdminActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initUi() {
        img_admin = view.findViewById(R.id.img_admin);
        tv_name_of_admin = view.findViewById(R.id.tv_name_of_admin);
    }

    private void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), Contants.accessToken));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String im = response.body().getUser().getImage().getUrl();
                    String name = response.body().getUser().getFullname();
                    if (im.isEmpty()) {
                        img_admin.setImageResource(R.drawable.backgroundslider);
                    } else {
                        Glide.with(getContext())
                                .load(im)
                                .into(img_admin);
                    }

                    tv_name_of_admin.setText(name);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }
}