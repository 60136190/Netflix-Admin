package com.example.adminnetflix.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.Comment;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommentFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<Comment> mCommentList;

    public ListCommentFilmAdapter(Context mContext, List<Comment> mCommentList) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);

        if (comment.getUser() == null) {
            ((ItemViewHolder)holder).constraintLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            ((ItemViewHolder)holder).constraintLayout.setVisibility(View.VISIBLE);
            String idUser = comment.getUser().getId();
            String strComment = comment.getContent();
            String nameOfUser = comment.getUser().getFullname();
            String strImgUser = comment.getUser().getImage().getUrl();
            String strIdComment = comment.getId();
            String strIdFilm = comment.getFilm();
            String strDate = comment.getCreatedAt();
            String[] parts = strDate.split("T");
            String part1 = parts[0]; // 004

            ((ItemViewHolder) holder).tvComment.setText(strComment);
            ((ItemViewHolder) holder).tvNameOfUser.setText(nameOfUser);
            ((ItemViewHolder) holder).tvDate.setText(part1);
            Picasso.with(mContext)
                    .load(strImgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).imgUser);

        }
    }


    @Override
    public int getItemCount() {
        if (mCommentList != null) {
            return mCommentList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgUser;
        public TextView tvNameOfUser;
        public TextView tvDate;
        public TextView tvComment;
        public ConstraintLayout constraintLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            tvNameOfUser = itemView.findViewById(R.id.tv_name_of_user);
            tvDate = itemView.findViewById(R.id.tv_date_comment);
            tvComment = itemView.findViewById(R.id.tv_comment);
            constraintLayout = itemView.findViewById(R.id.ct_lis_comment);
        }
    }

}
