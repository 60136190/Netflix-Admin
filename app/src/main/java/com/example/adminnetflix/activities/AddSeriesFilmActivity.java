package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.adminnetflix.R;

public class AddSeriesFilmActivity extends AppCompatActivity {

    EditText edtEpisode;
    ImageView imgImageSeries, imgVideoSeriesFilm;
    Button btnCreateSeriesFilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series_film);
        intiUi();

        btnCreateSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void intiUi() {
        edtEpisode = findViewById(R.id.edt_episode);
        imgImageSeries = findViewById(R.id.img_series);
        imgVideoSeriesFilm = findViewById(R.id.img_video_series);
        btnCreateSeriesFilm = findViewById(R.id.btn_create);
    }
    
    public void createSeriesFilm(){

    }
}