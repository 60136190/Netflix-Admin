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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.UpdateActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ModeOfPayment;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListModeOfPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ModeOfPayment> mModeOfPaymentList;

    public ListModeOfPaymentAdapter(Context mContext, List<ModeOfPayment> mModeOfPaymentList) {
        this.mContext = mContext;
        this.mModeOfPaymentList = mModeOfPaymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mode_of_payment, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModeOfPayment modeOfPayment = mModeOfPaymentList.get(position);

        ((ItemViewHolder) holder).itemModeOfPayment.setText(modeOfPayment.getName());
        Picasso.with(mContext)
                .load(modeOfPayment.getImage().getUrl()).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImgModeOfPayment);

        ((ItemViewHolder) holder).itemLnDelete.setOnClickListener(new View.OnClickListener() {
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

                ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnLogout = dialog.findViewById(R.id.btn_confirm_delete);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();
                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> listFavoriteFilmResponseCall = ApiClient.getFilmService().deleteModeOfPayment(
                                StoreUtil.get(view.getContext(), Contants.accessToken), modeOfPayment.getId());
                        listFavoriteFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                mModeOfPaymentList.remove(holder.getAdapterPosition());
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
        ((ItemViewHolder) holder).ctModeOfPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateActivity.class);
                intent.putExtra("update","Update Mode of Payment");
                intent.putExtra("id_mode_of_payment",modeOfPayment.getId());
                intent.putExtra("name_mode_of_payment",modeOfPayment.getName());
                intent.putExtra("public_Id_mode_of_payment",modeOfPayment.getImage().getPublicId());
                intent.putExtra("url_mode_of_payment",modeOfPayment.getImage().getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mModeOfPaymentList != null) {
            return mModeOfPaymentList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemModeOfPayment;
        private ImageView itemImgModeOfPayment;
        private LinearLayout itemLnDelete;
        private ConstraintLayout ctModeOfPayment;


        public ItemViewHolder(View itemView) {
            super(itemView);
            itemModeOfPayment = itemView.findViewById(R.id.tv_mode_of_payment);
            itemImgModeOfPayment = itemView.findViewById(R.id.img_mode_of_payment);
            itemLnDelete = itemView.findViewById(R.id.ln_delete);
            ctModeOfPayment = itemView.findViewById(R.id.ct_mode_of_paymeny);
        }
    }

}
