package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;

import com.example.adminnetflix.activities.ListDetailActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.comment.CommentDeleted;
import com.example.adminnetflix.models.response.comment.CommentDeletedResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommentDeletedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<CommentDeleted> commentDeleteds;

    public ListCommentDeletedAdapter(Context mContext, List<CommentDeleted> commentDeleteds) {
        this.mContext = mContext;
        this.commentDeleteds = commentDeleteds;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_deleted, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentDeleted commentDeleted = commentDeleteds.get(position);
        String idComment = commentDeleted.getId();
        String nameOfUser = commentDeleted.getUser().getFullname();
        String imgUser = commentDeleted.getUser().getImage().getUrl();
        String time = commentDeleted.getCreatedAt();
//        String title = commentDeleted.getFilm().getTitle();
        String content = commentDeleted.getContent();

            ((ItemViewHolder) holder).tvNameOfUser.setText(nameOfUser);
            ((ItemViewHolder) holder).tvTime.setText(time.substring(1,10));
            ((ItemViewHolder) holder).tvContent.setText(content);
            ((ItemViewHolder) holder).imgRestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<ResponseDTO> commentDeletedResponseCall = ApiClient.getFilmService().restoreComment(
                            StoreUtil.get(mContext, Contants.accessToken),idComment);
                    commentDeletedResponseCall.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                            commentDeleteds.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                            Toast.makeText(mContext, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
//            ((ItemViewHolder) holder).tvTileFilm.setText(String.valueOf(title));

            Picasso.with(mContext)
                    .load(imgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).imgUser);

    }


    @Override
    public int getItemCount() {
        if (commentDeleteds != null) {
            return commentDeleteds.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
         ImageView imgUser, imgRestore;
         TextView tvNameOfUser,tvTime, tvContent, tvTileFilm;
         ConstraintLayout constraintLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            imgRestore = itemView.findViewById(R.id.img_restore);
            tvNameOfUser = itemView.findViewById(R.id.tv_name_of_user);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTileFilm = itemView.findViewById(R.id.tv_title);
            constraintLayout = itemView.findViewById(R.id.ct_lis_comment_deleted);
        }
    }

}
