package com.example.adminnetflix.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.UpdateActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.Category;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoriesFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Category> mCategoriesList;

    public ListCategoriesFilmAdapter(Context mContext, List<Category> mCategoriesList) {
        this.mContext = mContext;
        this.mCategoriesList = mCategoriesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_categories_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = mCategoriesList.get(position);
        String title = category.getName();

        ((ItemViewHolder) holder).itemTitle.setText(title);
        ((ItemViewHolder) holder).itemNo.setText(String.valueOf(position+1));
        ((ItemViewHolder) holder).lnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirm);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtribute = window.getAttributes();
                window.setAttributes(windowAtribute);

                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnDelete = dialog.findViewById(R.id.btn_confirm_delete);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> listFavoriteFilmResponseCall = ApiClient.getFilmService().deleteCategory(
                                StoreUtil.get(view.getContext(), Contants.accessToken), category.getId());
                        listFavoriteFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                mCategoriesList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
            }
        });
        ((ItemViewHolder) holder).ctListCategoriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateActivity.class);
                intent.putExtra("update","Update Category");
                intent.putExtra("id_category",category.getId());
                intent.putExtra("name_category",category.getName());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mCategoriesList != null){
            return mCategoriesList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemTitle;
        private TextView itemNo;
        private ConstraintLayout ctListCategoriesFilm;
        private LinearLayout lnDeleteCategory;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_category);
            itemTitle = itemView.findViewById(R.id.tv_title_category);
            itemNo = itemView.findViewById(R.id.tv_no);
            ctListCategoriesFilm = itemView.findViewById(R.id.ct_list_categories_film);
            lnDeleteCategory = itemView.findViewById(R.id.ln_delete);
        }
    }

}
