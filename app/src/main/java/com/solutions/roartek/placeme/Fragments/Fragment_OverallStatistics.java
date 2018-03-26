package com.solutions.roartek.placeme.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.DTO.StatisticsDTO;
import com.solutions.roartek.placeme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swathi.K on 23-12-2016.
 */
public class Fragment_OverallStatistics extends Fragment{
    private View view;
    private PieChart pieChart;
    private StatisticsDTO statisticsDTO;
    private List<String> studentsXLabel;
    private List<String> companyXLabel;
    private List<Entry> studentValues;
    private List<Entry> companyValues;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  setRetainInstance(true);
        view =  inflater.inflate(R.layout.fragment_overall_statistics,container,false);
        pieChart= (PieChart) view.findViewById(R.id.overviewPieChart);
        initialisePieChart();
        return view;
    }

    private void initialisePieChart()
    {
        pieChart.clear();
        pieChart.highlightValues(null);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
        pieChart.setDescription("");

        Legend legend=pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

    }

    public Fragment_OverallStatistics() {
    }

    public Fragment_OverallStatistics(StatisticsDTO statisticsDTO) {
        this.statisticsDTO = statisticsDTO;
        prepareDataForChart();
    }

    public PieChart getPieChart(String statsMode) {
        initialisePieChart();
        this.pieChart.setData(getPieData(statsMode));
        return pieChart;
    }

    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    private void prepareDataForChart()
    {
        studentsXLabel =new ArrayList<>();
        studentsXLabel.add("Placed");
        studentsXLabel.add("Non-Placed");

        studentValues=new ArrayList<>();
        studentValues.add(new Entry(statisticsDTO.getPlacedCount(),0));
        studentValues.add(new Entry(statisticsDTO.getNonPlacedCount(),1));

        companyXLabel =new ArrayList<>();
        companyXLabel.add("Confirmed");
        companyXLabel.add("UpComing");

        companyValues=new ArrayList<>();
        companyValues.add(new Entry(statisticsDTO.getConfirmedCompCount(),0));
        companyValues.add(new Entry(statisticsDTO.getUpcomingCompCount(),1));
    }

    private List<String> getXValues(String statsMode)
    {
        if(statsMode== Constants.STATS_MODE_STUDENT)
            return studentsXLabel;
        else
            return companyXLabel;
    }

    private PieDataSet getPieDataSet(String statsMode)
    {
        PieDataSet pieDataSet;
        if(statsMode==Constants.STATS_MODE_STUDENT)
        {
            pieDataSet=new PieDataSet(studentValues,"");
            if(statisticsDTO.getPlacedCount()==0 && statisticsDTO.getNonPlacedCount()==0)
            {
                this.pieChart.setVisibility(View.GONE);
                Utility.showToast(getActivity(),Constants.VALIDATION_MSG_NO_STUDENTS);

            }
            else {
                this.pieChart.setVisibility(View.VISIBLE);
                pieDataSet.setSliceSpace(studentValues.size());
            }
        }
        else
        {
            pieDataSet=new PieDataSet(companyValues,"");
            if(statisticsDTO.getConfirmedCompCount()==0 && statisticsDTO.getUpcomingCompCount()==0)
            {
                this.pieChart.setVisibility(View.GONE);
                Utility.showToast(getActivity(), Constants.VALIDATION_MSG_NO_COMPANIES);
            }
            else
            {
                this.pieChart.setVisibility(View.VISIBLE);
                pieDataSet.setSliceSpace(companyValues.size());
            }
        }
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        return pieDataSet;
    }

    public PieData getPieData(String statsMode)
    {
        PieData pieData=new PieData(getXValues(statsMode),getPieDataSet(statsMode));
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        return pieData;
    }

}
