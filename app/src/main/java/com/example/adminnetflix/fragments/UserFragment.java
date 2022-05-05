package com.example.adminnetflix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListManagerAdapter;
import com.example.adminnetflix.models.ItemManagerModel;
import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private RecyclerView rcvManager;
    ListManagerAdapter listManagerAdapter;
    List<ItemManagerModel> itemManagerModel;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initUi();
        setDataRcvManager();

        return view;
    }

    private void initUi() {
        rcvManager = view.findViewById(R.id.rcv_manager);
    }

    private void setDataRcvManager() {
        itemManagerModel = new ArrayList<>();
        itemManagerModel.add(new ItemManagerModel("User","12", R.drawable.img, R.color.user));
        itemManagerModel.add(new ItemManagerModel("Admin", "12", R.drawable.admin, R.color.admin));
        itemManagerModel.add(new ItemManagerModel("Director", "12", R.drawable.user, R.color.director));
        itemManagerModel.add(new ItemManagerModel("Rating", "12", R.drawable.backgroundslider, R.color.rating));
        itemManagerModel.add(new ItemManagerModel("Feedback", "12", R.drawable.feedback, R.color.feedback));
        itemManagerModel.add(new ItemManagerModel("Mode of payment", "12", R.drawable.payment, R.color.mode_of_payment));
        itemManagerModel.add(new ItemManagerModel("Category", "12", R.drawable.category, R.color.category));
        listManagerAdapter = new ListManagerAdapter(getContext(), itemManagerModel);
        rcvManager.setAdapter(listManagerAdapter);
        rcvManager.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

}