package com.solutions.roartek.placeme.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.DTO.StatisticsDTO;
import com.solutions.roartek.placeme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swathi.K on 23-12-2016.
 */
public class Fragment_DetailStatistics extends Fragment {
    private View view;
    private BarChart barChart;
    private StatisticsDTO statisticsDTO;
    private List<String> studentsXLabel;
    private List<String> companyXLabel;
    private List<BarEntry> studentValues;
    private List<BarEntry> companyValues;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_detail_statistics,container,false);
        barChart= (BarChart) view.findViewById(R.id.detailedBarChart);
        return view;
    }

    public Fragment_DetailStatistics() {
    }

    public Fragment_DetailStatistics(StatisticsDTO statisticsDTO) {
        this.statisticsDTO = statisticsDTO;
        prepareDataForChart();
    }

    public BarChart getBarChart(String statsMode) {
        initialiseBarChart();
        barChart.setData(getBarData(statsMode));
        return barChart;
    }

    private void initialiseBarChart() {

        barChart.setVisibleXRange(20);
        barChart.isHorizontalScrollBarEnabled();
        barChart.isDoubleTapToZoomEnabled();
        barChart.setDescription("");
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    private void prepareDataForChart()
    {
        int ctr=-1;
        studentsXLabel =new ArrayList<>();
        studentValues=new ArrayList<>();
        companyXLabel =new ArrayList<>();
        companyValues=new ArrayList<>();

        if(statisticsDTO.getStudentBranchMap()!=null && statisticsDTO.getStudentBranchMap().size()>0)
        {
            studentsXLabel =new ArrayList<>(statisticsDTO.getStudentBranchMap().keySet());
            studentValues=new ArrayList<>();
            for (Integer count:statisticsDTO.getStudentBranchMap().values())
                studentValues.add(new BarEntry(count,++ctr));
        }

        if(statisticsDTO.getStudentCompanyMap()!=null && statisticsDTO.getStudentCompanyMap().size()>0)
        {
            ctr=-1;
            companyXLabel =new ArrayList<>(statisticsDTO.getStudentCompanyMap().keySet());
            companyValues=new ArrayList<>();
            for (Integer count:statisticsDTO.getStudentCompanyMap().values())
                companyValues.add(new BarEntry(count,++ctr));
        }

        studentsXLabel.add("AA");
        studentsXLabel.add("BB");
        studentsXLabel.add("CC");studentsXLabel.add("DD");studentsXLabel.add("EE");studentsXLabel.add("FF");
        studentsXLabel.add("GG");studentsXLabel.add("HH");studentsXLabel.add("KK");studentsXLabel.add("OO");
        studentsXLabel.add("ZZ");studentsXLabel.add("XX");studentsXLabel.add("UU");studentsXLabel.add("II");

        studentValues.add(new BarEntry(3,2));studentValues.add(new BarEntry(13,3));studentValues.add(new BarEntry(1,4));studentValues.add(new BarEntry(25,5));
        studentValues.add(new BarEntry(9,6));studentValues.add(new BarEntry(88,7));
        studentValues.add(new BarEntry(35,8));studentValues.add(new BarEntry(56,9));
        studentValues.add(new BarEntry(4,10));studentValues.add(new BarEntry(3,11));
        studentValues.add(new BarEntry(6,12));studentValues.add(new BarEntry(1,13));
    }

    private List<String> getXValues(String statsMode)
    {
        if(statsMode== Constants.STATS_MODE_STUDENT)
            return studentsXLabel;
        else
            return companyXLabel;
    }

    private BarDataSet getBarDataSet(String statsMode) {
        BarDataSet barDataSet;
        if (statsMode == Constants.STATS_MODE_STUDENT) {
            barDataSet = new BarDataSet(studentValues, "Branches");

        } else {
            barDataSet = new BarDataSet(companyValues, "Companies");
        }
        barDataSet.setColors(MyConfig.PIE_CHART_COLORS);
        return barDataSet;
    }

    private BarData getBarData(String statsMode) {
        BarData barData = new BarData(getXValues(statsMode), getBarDataSet(statsMode));
        if (barData.getXValCount() > 6)
            barChart.zoom(1.4f, 0, 1f, 1f);
        else
            barChart.zoomOut();
        return barData;
    }
}
