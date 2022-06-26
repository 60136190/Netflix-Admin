package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListManagerStatisticalAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.MonthlyRevenueResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRevenueActivity extends AppCompatActivity {

    private ValueLineSeries series;
    private ValueLineChart valueLineChartRevenue;
    public static float a,b,c,d,e,f,g,h,n,k,l,m;
    TextView jan,feb,mar,apr,may,jun,july,aug,sep,oct,nov,dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_revenue);
        initUi();
        setDataOnLineChart();
    }

    private void initUi() {
        valueLineChartRevenue= findViewById(R.id.chart_line);
        jan = findViewById(R.id.tv_jan);
        feb = findViewById(R.id.tv_feb);
        mar = findViewById(R.id.tv_mar);
        apr = findViewById(R.id.tv_apr);
        may = findViewById(R.id.tv_may);
        jun = findViewById(R.id.tv_jun);
        july = findViewById(R.id.tv_july);
        aug = findViewById(R.id.tv_aug);
        sep = findViewById(R.id.tv_sep);
        oct = findViewById(R.id.tv_oct);
        nov = findViewById(R.id.tv_nov);
        dec = findViewById(R.id.tv_dec);
    }
    private void setDataOnLineChart(){
        Call<MonthlyRevenueResponse> responseDTOCall = ApiClient.getUserService().getMonthlyRevenue(
                StoreUtil.get(DetailRevenueActivity.this, Contants.accessToken));
        responseDTOCall.enqueue(new Callback<MonthlyRevenueResponse>() {
            @Override
            public void onResponse(Call<MonthlyRevenueResponse> call, Response<MonthlyRevenueResponse> response) {
                for (int i=0;i<response.body().getData().size();i++){

                    switch (response.body().getData().get(i).getId()){
                        case 1:
                            a = response.body().getData().get(i).getRevenue();
                            jan.setText(String.valueOf(a+" $"));
                            break;
                        case 2:
                            b = response.body().getData().get(i).getRevenue();
                            feb.setText(String.valueOf(b+" $"));
                            break;
                        case 3:
                            c = response.body().getData().get(i).getRevenue();
                            mar.setText(String.valueOf(c+" $"));
                            break;
                        case 4:
                            d = response.body().getData().get(i).getRevenue();
                            apr.setText(String.valueOf(d+" $"));
                            break;
                        case 5:
                            e = response.body().getData().get(i).getRevenue();
                            may.setText(String.valueOf(e+" $"));
                            break;
                        case 6:
                            f = response.body().getData().get(i).getRevenue();
                            jun.setText(String.valueOf(f+" $"));
                            break;
                        case 7:
                            g = response.body().getData().get(i).getRevenue();
                            july.setText(String.valueOf(g+" $"));
                            break;
                        case 8:
                            h = response.body().getData().get(i).getRevenue();
                            aug.setText(String.valueOf(h+" $"));
                            break;
                        case 9:
                            n = response.body().getData().get(i).getRevenue();
                            sep.setText(String.valueOf(n+" $"));
                            break;
                        case 10:
                            k = response.body().getData().get(i).getRevenue();
                            oct.setText(String.valueOf(k+" $"));
                            break;
                        case 11:
                            l = response.body().getData().get(i).getRevenue();
                            nov.setText(String.valueOf(l+" $"));
                            break;
                        case 12:
                            m = response.body().getData().get(i).getRevenue();
                            dec.setText(String.valueOf(m+" $"));
                            break;
                    }
                    series = new ValueLineSeries();
                    series.setColor(0xFFf37869);
                    series.addPoint(new ValueLinePoint("1", 0));
                    series.addPoint(new ValueLinePoint("1", a));
                    series.addPoint(new ValueLinePoint("2", b));
                    series.addPoint(new ValueLinePoint("3", c));
                    series.addPoint(new ValueLinePoint("4", d));
                    series.addPoint(new ValueLinePoint("5", e));
                    series.addPoint(new ValueLinePoint("6", f));
                    series.addPoint(new ValueLinePoint("7", g));
                    series.addPoint(new ValueLinePoint("8", h));
                    series.addPoint(new ValueLinePoint("9", n));
                    series.addPoint(new ValueLinePoint("10", k));
                    series.addPoint(new ValueLinePoint("11", l));
                    series.addPoint(new ValueLinePoint("12", m));
                    series.addPoint(new ValueLinePoint("12", 0));

                    valueLineChartRevenue.addSeries(series);
                    valueLineChartRevenue.startAnimation();
                }
            }

            @Override
            public void onFailure(Call<MonthlyRevenueResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}