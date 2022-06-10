package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.ResultFilm;

import java.util.List;

public class AllFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ResultFilm> resultFilms;


    public AllFilmAdapter(Context mContext, List<ResultFilm> resultFilms) {
        this.mContext = mContext;
        this.resultFilms = resultFilms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_film, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ResultFilm resultFilm = resultFilms.get(position);
        ((ItemViewHolder) holder).tvTitle.setText(resultFilm.getCategory());

        DetailFilmAdapter adapter;
        adapter = new DetailFilmAdapter(mContext, resultFilm.getData());
        ((ItemViewHolder) holder).rcv_detail.setAdapter(adapter);
        ((ItemViewHolder) holder).rcv_detail.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ((ItemViewHolder) holder).rcv_detail.setHasFixedSize(true);

    }


    @Override
    public int getItemCount() {
        if (resultFilms != null) {
            return resultFilms.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        RecyclerView rcv_detail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rcv_detail = itemView.findViewById(R.id.rcv_film);
        }
    }

}
