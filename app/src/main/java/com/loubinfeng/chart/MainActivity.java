package com.loubinfeng.chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.loubinfeng.chart.lib.pie.PieChart;
import com.loubinfeng.chart.lib.pie.PieData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (PieChart)findViewById(R.id.pie);
        List<PieData> data = new ArrayList<>();
        data.add(new PieData("p1",25));
        data.add(new PieData("p2",30));
        data.add(new PieData("p3",30));
        data.add(new PieData("p4",15));
        chart.setData(data);
    }
}
