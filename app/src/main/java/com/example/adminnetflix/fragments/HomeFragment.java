package com.example.adminnetflix.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.InformationAdminActivity;
import com.example.adminnetflix.activities.manage.AdminActivity;
import com.example.adminnetflix.activities.manage.CategoryActivity;
import com.example.adminnetflix.activities.manage.DirectorActivity;
import com.example.adminnetflix.activities.manage.FavoriteActivity;
import com.example.adminnetflix.activities.manage.FeedbackActivity;
import com.example.adminnetflix.activities.manage.ModeOfPaymentActivity;
import com.example.adminnetflix.activities.manage.UserActivity;
import com.example.adminnetflix.adapters.ListAdminAdapter;
import com.example.adminnetflix.adapters.ListCategoriesFilmAdapter;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.adapters.ListFavoritelFilmAdapter;
import com.example.adminnetflix.adapters.ListFeedbackAdapter;
import com.example.adminnetflix.adapters.ListManagerAdapter;
import com.example.adminnetflix.adapters.ListManagerStatisticalAdapter;
import com.example.adminnetflix.adapters.ListModeOfPaymentAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.ItemManageStatistical;
import com.example.adminnetflix.models.ItemManagerModel;
import com.example.adminnetflix.models.response.FeedbackResponse;
import com.example.adminnetflix.models.response.FilmResponse;
import com.example.adminnetflix.models.response.ListAdminResponse;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.models.response.ModeOfPaymentResponse;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.models.response.favourite.FavouriteResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private View view;
    ImageView img_admin;
    TextView tv_name_of_admin;
    TextView tvTotalUser, tvTotalAdmin, tvTotalDirector, tvUserUncheck;
    TextView tvTotalFilmKid, tvTotalFilmAdult;
    private ListCategoriesFilmAdapter listCategoriesFilmAdapter;
    private ListAdminAdapter listAdminAdapter;
    private ListUserAdapter listUserAdapter;
    private ListDirectorAdapter listDirectorAdapter;
    private ListFeedbackAdapter listFeedbackAdapter;
    private ListModeOfPaymentAdapter listModeOfPaymentAdapter;
    private ValueLineSeries series;
    private ValueLineSeries userSub;
    private PieChart mPieChart;
    private ValueLineChart valueLineChartRevenue;
    private ValueLineChart valueLineChartUserSub;
    List<ItemManageStatistical> itemManageStatisticalList;
    private ListManagerStatisticalAdapter listManagerStatisticalAdapter;
    RecyclerView rcvManage;
    int category;
    int feedback;
    int director;
    int admin;
    int user;
    int modePayment;
    int filmKid;
    int filmAdult;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        itemManageStatisticalList = new ArrayList<>();
        initUi();
        setDataOnLineChart();
        setDataOnLineChartUserSub();
        getProfile();
        getListCategory();
        getListFeedback();
        getListModeOfPayment();
        getListAdmin();
        getListUser();
        getListDirector();
        getFilmAdult();
        getFilmKid();

        // config layout of manage list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvManage.setLayoutManager(linearLayoutManager);

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
        valueLineChartRevenue= view.findViewById(R.id.chart_line);
        valueLineChartUserSub = view.findViewById(R.id.chart_line_user_sub);
        img_admin = view.findViewById(R.id.img_admin);
        tv_name_of_admin = view.findViewById(R.id.tv_name_of_admin);
        mPieChart = view.findViewById(R.id.chart);
        tvTotalUser = view.findViewById(R.id.tv_total_user);
        tvTotalAdmin = view.findViewById(R.id.tv_total_admin);
        tvTotalDirector = view.findViewById(R.id.tv_total_director);
        rcvManage = view.findViewById(R.id.rcv_manage);
        tvTotalFilmKid = view.findViewById(R.id.tv_total_film_kid);
        tvTotalFilmAdult = view.findViewById(R.id.tv_total_film_adult);
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
                        Glide.with(getActivity())
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

    public void getListCategory() {
        Call<ListCategories> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListCategoriesFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                listCategoriesFilmAdapter = new ListCategoriesFilmAdapter(getContext(),response.body().getData());
                category = listCategoriesFilmAdapter.getItemCount();

                itemManageStatisticalList.add(new ItemManageStatistical("Total category"
                        , "https://res.cloudinary.com/fee/image/upload/v1654953567/Public%20Images/category_lxgjnl.png"
                        , category, R.color.yellow));
                listManagerStatisticalAdapter = new ListManagerStatisticalAdapter(getContext(), itemManageStatisticalList);
                rcvManage.setAdapter(listManagerStatisticalAdapter);
            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListFeedback() {
        Call<FeedbackResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllFeedback(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                listFeedbackAdapter = new ListFeedbackAdapter(getContext(),response.body().getData());
                feedback = listFeedbackAdapter.getItemCount();

                itemManageStatisticalList.add(new ItemManageStatistical("Total feedback"
                        ,"https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/feedback_invz9x.png"
                        , feedback , R.color.second_red));
                listManagerStatisticalAdapter = new ListManagerStatisticalAdapter(getContext(), itemManageStatisticalList);
                rcvManage.setAdapter(listManagerStatisticalAdapter);

            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListModeOfPayment() {
        Call<ModeOfPaymentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getModeOfPayment(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ModeOfPaymentResponse>() {
            @Override
            public void onResponse(Call<ModeOfPaymentResponse> call, Response<ModeOfPaymentResponse> response) {
                listModeOfPaymentAdapter = new ListModeOfPaymentAdapter(getContext(),response.body().getData());
                modePayment = listModeOfPaymentAdapter.getItemCount();
                itemManageStatisticalList.add(new ItemManageStatistical("Total mode payment"
                        ,"https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/payment_dwvqvm.png"
                        , modePayment , R.color.blue_gray));
                listManagerStatisticalAdapter = new ListManagerStatisticalAdapter(getContext(), itemManageStatisticalList);
                rcvManage.setAdapter(listManagerStatisticalAdapter);

            }

            @Override
            public void onFailure(Call<ModeOfPaymentResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getListAdmin() {
        Call<ListAdminResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListAdmin(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListAdminResponse>() {
            @Override
            public void onResponse(Call<ListAdminResponse> call, Response<ListAdminResponse> response) {
                listAdminAdapter = new ListAdminAdapter(getContext(),response.body().getData());
                admin = listAdminAdapter.getItemCount();
                tvTotalAdmin.setText(String.valueOf(admin));
            }

            @Override
            public void onFailure(Call<ListAdminResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getListUser() {
        Call<ListUserResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListUser(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                listUserAdapter = new ListUserAdapter(getContext(),response.body().getData());
                user = listUserAdapter.getItemCount();
                tvTotalUser.setText(String.valueOf(user));

            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getListDirector() {
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(getContext(), Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                listDirectorAdapter = new ListDirectorAdapter(getContext(), response.body().getData());
                tvTotalDirector.setText(String.valueOf(listDirectorAdapter.getItemCount()));
                director = listDirectorAdapter.getItemCount();
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFilmAdult(){
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                filmAdult = response.body().getData().size();
                mPieChart.addPieSlice(new PieModel("Film adult",
                        (float) Double.parseDouble(String.valueOf(filmAdult))
                        ,getResources().getColor(R.color.red)));
                tvTotalFilmAdult.setText(String.valueOf(filmAdult));
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getFilmKid(){
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getFilmKid(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                filmKid = response.body().getData().size();
                mPieChart.addPieSlice(new PieModel("Film kid",
                        (float) Double.parseDouble(String.valueOf(filmKid))
                        ,getResources().getColor(R.color.yellow)));

                tvTotalFilmKid.setText(String.valueOf(filmKid));
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setDataOnLineChart(){
        series = new ValueLineSeries();
        series.setColor(0xFF47daf0);
        series.addPoint(new ValueLinePoint("0", 0));
        series.addPoint(new ValueLinePoint("Jan", (float) Long.parseLong(String.valueOf(category))));
        series.addPoint(new ValueLinePoint("Feb", 20));
        series.addPoint(new ValueLinePoint("Mar", 9));
        series.addPoint(new ValueLinePoint("Apr", 20));
        series.addPoint(new ValueLinePoint("May", 39));
        series.addPoint(new ValueLinePoint("Jun", 58));
        series.addPoint(new ValueLinePoint("Jul", 100));
        series.addPoint(new ValueLinePoint("Aug", 1));
        series.addPoint(new ValueLinePoint("Sep", 28));
        series.addPoint(new ValueLinePoint("0", 0));
        valueLineChartRevenue.addSeries(series);
        valueLineChartRevenue.startAnimation();
    }

    private void setDataOnLineChartUserSub(){
        userSub = new ValueLineSeries();
        userSub.setColor(0xFFf37869);
        userSub.addPoint(new ValueLinePoint("0", 0));
        userSub.addPoint(new ValueLinePoint("Jan", (float) Long.parseLong(String.valueOf(category))));
        userSub.addPoint(new ValueLinePoint("Feb", 90));
        userSub.addPoint(new ValueLinePoint("Mar", 9));
        userSub.addPoint(new ValueLinePoint("Apr", 1));
        userSub.addPoint(new ValueLinePoint("May", 39));
        userSub.addPoint(new ValueLinePoint("Jun", 58));
        userSub.addPoint(new ValueLinePoint("Jul", 20));
        userSub.addPoint(new ValueLinePoint("Aug", 100));
        userSub.addPoint(new ValueLinePoint("Sep", 32));
        userSub.addPoint(new ValueLinePoint("0", 0));
        valueLineChartUserSub.addSeries(userSub);
        valueLineChartUserSub.startAnimation();
    }

}