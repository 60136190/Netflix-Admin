package com.example.adminnetflix.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.AddFilmActivity;
import com.example.adminnetflix.adapters.FilmAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.FilmResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private View view;
    private RecyclerView rcvAllFilm;
    private FilmAdapter filmAdapter;
    private Button btnAddFilm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initUi();
        getDataAllFilm();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvAllFilm.setLayoutManager(gridLayoutManager);

        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddFilmActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initUi(){
        rcvAllFilm = view.findViewById(R.id.rcv_all_film);
        btnAddFilm = view.findViewById(R.id.btn_add_film);
    }

    private void getDataAllFilm() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                filmAdapter = new FilmAdapter(getContext(),response.body().getData());
                rcvAllFilm.setAdapter(filmAdapter);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



}