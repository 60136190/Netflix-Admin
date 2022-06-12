package com.example.adminnetflix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.manage.CategoryActivity;
import com.example.adminnetflix.adapters.ListManagerAdapter;
import com.example.adminnetflix.models.ItemManagerModel;
import java.util.ArrayList;
import java.util.List;

public class ManagerFragment extends Fragment {
    private RecyclerView rcvManager;
    ListManagerAdapter listManagerAdapter;
    List<ItemManagerModel> itemManagerModel;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage, container, false);
        initUi();
        setDataRcvManager();
        return view;
    }

    private void initUi() {
        rcvManager = view.findViewById(R.id.rcv_manager);
    }

    private void setDataRcvManager() {
        itemManagerModel = new ArrayList<>();
        itemManagerModel.add(new ItemManagerModel("User", "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/customer_p8fmok.png", R.color.user));
        itemManagerModel.add(new ItemManagerModel("Admin",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/admin_jvo7by.png", R.color.admin));
        itemManagerModel.add(new ItemManagerModel("Director",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/director_dbcjkw.png", R.color.director));
        itemManagerModel.add(new ItemManagerModel("Rating",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/rating_tchrv8.png", R.color.rating));
        itemManagerModel.add(new ItemManagerModel("Feedback",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/feedback_invz9x.png", R.color.feedback));
        itemManagerModel.add(new ItemManagerModel("Mode of payment",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/payment_dwvqvm.png", R.color.mode_of_payment));
        itemManagerModel.add(new ItemManagerModel("Category",  "https://res.cloudinary.com/fee/image/upload/v1654953567/Public%20Images/category_lxgjnl.png", R.color.category));
        itemManagerModel.add(new ItemManagerModel("Comment",  "https://res.cloudinary.com/fee/image/upload/v1654953543/Public%20Images/comment_b13cjz.png", R.color.yellow));
        listManagerAdapter = new ListManagerAdapter(getContext(), itemManagerModel);
        rcvManager.setAdapter(listManagerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvManager.setLayoutManager(linearLayoutManager);
    }

}