package com.example.adminnetflix.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.AllFilmAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.AllFilmResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private View view;
    private RecyclerView rcvAllFilm;
    private Button btnToTop;
    private AllFilmAdapter testAdapter;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initUi();
        setProgressBar();
        getDataAllFilm();
        rcvAllFilm.setOnTouchListener(new TranslateAnimationUtil(getContext(),btnToTop));
        rcvAllFilm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvAllFilm.setHasFixedSize(true);

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
        progressBar = view.findViewById(R.id.spin_kit);

    }

    private void getDataAllFilm() {
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                if (response.isSuccessful()){
                    testAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                    rcvAllFilm.setAdapter(testAdapter);
                }

            }

            @Override
            public void onFailure(Call<AllFilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setProgressBar(){
        Sprite cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    current = 0;
                }
                progressBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                getDataAllFilm();

            }

        };
        countDownTimer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataAllFilm();
    }
}