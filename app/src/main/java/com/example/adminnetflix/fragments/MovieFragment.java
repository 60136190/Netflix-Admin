package com.example.adminnetflix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.FilmAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.FilmResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private View view;
    private RecyclerView rcvAllFilm;
    private Button btnToTop;
    private FilmAdapter filmAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initUi();
        getDataAllFilm();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvAllFilm.setLayoutManager(gridLayoutManager);

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAllFilm.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    private void initUi(){
        rcvAllFilm = view.findViewById(R.id.rcv_all_film);
        btnToTop = view.findViewById(R.id.btn_to_top);
    }

    private void getDataAllFilm() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                filmAdapter = new FilmAdapter(getContext(),response.body().getData());
                rcvAllFilm.setAdapter(filmAdapter);
                rcvAllFilm.setOnTouchListener(new TranslateAnimationUtil(getContext(),btnToTop));

            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



}