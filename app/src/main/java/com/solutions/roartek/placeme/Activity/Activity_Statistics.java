package com.solutions.roartek.placeme.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.solutions.roartek.placeme.Adapter.Adapter_Statistics;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.DTO.StatisticsDTO;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.Fragments.Fragment_DetailStatistics;
import com.solutions.roartek.placeme.Fragments.Fragment_OverallStatistics;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Activity_Statistics extends AppCompatActivity implements GenericActivityDelegate,ResponseDelegate,View.OnClickListener{

    private ViewPager statisticsViewPager;
    private Adapter_Statistics statisticsAdapter;
    private Fragment_OverallStatistics fragment_overallStatistics;
    private Fragment_DetailStatistics fragment_detailStatistics;
    private LinearLayout pieLayout,barLayout,iconLayout;
    private TextView studentIcon,companyIcon,monthlyIcon,yearlyIcon;
    private BarChart barChart;
    private Chart currentChart;
    private TabLayout tabLayout;
    private CountDownTimer countDownTimer;
    private int iconLayoutDuration=5000;
    private PreferenceManager preferenceManager;
    private Entity_Staff entityStaff;
    private StatisticsDTO statisticsDTO;
    private AppManager appManager;
    private String statsMode;

    private List<String> monthlyXLabel;
    private List<String> yearlyXLabel;
    private ArrayList<BarDataSet> yearlyDataSet;
    private ArrayList<BarDataSet> monthlyDataSet;
    private int[] barChartColorArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initialiseViews();
        setPreferences();
        getObjectFromPreferences();
        onProceed();
    }

    private void statisticsTabView() {

        try {
            fragment_overallStatistics = new Fragment_OverallStatistics(statisticsDTO);
            fragment_detailStatistics = new Fragment_DetailStatistics(statisticsDTO);

            statisticsAdapter = new Adapter_Statistics(getSupportFragmentManager(),Activity_Statistics.this, fragment_overallStatistics, fragment_detailStatistics);
            statisticsViewPager.setAdapter(statisticsAdapter);
            tabLayout.setupWithViewPager(statisticsViewPager);

           loadOverallPieChart();
            onTabClick();
        }
        catch (Exception e)
        {
            Log.d("fs",e.getMessage());
        }
    }

    private void onTabClick() {
        statisticsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                   loadOverallPieChart();
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    currentChart = fragment_detailStatistics.getBarChart(statsMode);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistics:
                iconLayout.setVisibility(View.VISIBLE);
                Utility.startAnimation(this, iconLayout, R.anim.slide_up);
                countDownTimer.start();
                return true;
            case R.id.statistics_save:
                saveImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statistics_student_icon:
                statsMode=Constants.STATS_MODE_STUDENT;
                hideBarLayout();
                break;
            case R.id.statistics_company_icon:
                statsMode=Constants.STATS_MODE_COMPANY;
                hideBarLayout();
                break;
            case R.id.statistics_monthly_icon:
                statsMode=Constants.STATS_MODE_MONTHLY;
                hidePieLayout();
                break;
            case R.id.statistics_yearly_icon:
                statsMode=Constants.STATS_MODE_YEARLY;
                hidePieLayout();
                break;
          }
    }

    private void hideBarLayout() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        barLayout.setVisibility(View.GONE);
        pieLayout.setVisibility(View.VISIBLE);
        Utility.startAnimation(Activity_Statistics.this, pieLayout, R.anim.fade_in);

        if (statisticsViewPager.getCurrentItem() == 0) {
            loadOverallPieChart();
        } else
            statisticsViewPager.setCurrentItem(0);
    }

    private void hidePieLayout()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        pieLayout.setVisibility(View.GONE);
        barLayout.setVisibility(View.VISIBLE);
        Utility.startAnimation(Activity_Statistics.this, barLayout, R.anim.fade_in);
        populateBarChart();
    }

    private void initialiseTimer()
    {
        countDownTimer=new CountDownTimer(iconLayoutDuration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Utility.startAnimation(Activity_Statistics.this, iconLayout, R.anim.slide_down);
                iconLayout.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onProceed() {
        appManager=new AppManagerImpl();
        appManager.executeDBCalls(Activity_Statistics.this,entityStaff,this);
    }

    @Override
    public void initialiseViews() {
        statisticsViewPager = (ViewPager) findViewById(R.id.statistics_ViewPager);

        pieLayout = (LinearLayout) findViewById(R.id.statistics_pieLayout);
        barLayout= (LinearLayout) findViewById(R.id.statistics_barLayout);
        iconLayout = (LinearLayout) findViewById(R.id.statistics_iconLayout);
        tabLayout= (TabLayout) findViewById(R.id.statistics_tabLayout);

        studentIcon = (TextView) findViewById(R.id.statistics_student_icon);
        companyIcon = (TextView) findViewById(R.id.statistics_company_icon);
        monthlyIcon = (TextView) findViewById(R.id.statistics_monthly_icon);
        yearlyIcon = (TextView) findViewById(R.id.statistics_yearly_icon);

        barChart= (BarChart) findViewById(R.id.statistics_barchart);

        barLayout.setVisibility(View.GONE);
        iconLayout.setVisibility(View.GONE);

        studentIcon.setOnClickListener(this);
        companyIcon.setOnClickListener(this);
        monthlyIcon.setOnClickListener(this);
        yearlyIcon.setOnClickListener(this);

       initialiseTimer();
       barChartColorArray=getResources().getIntArray(R.array.overviewPieChartColors);
    }

    @Override
    public void setPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_Statistics.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, Constants.OPERATION_STATISTICS, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
    }

    @Override
    public void onPostExecute(Object responseObject) {
        if(responseObject instanceof StatisticsDTO) {
            statisticsDTO= (StatisticsDTO) responseObject;
            statsMode=Constants.STATS_MODE_STUDENT;
            statisticsTabView();
            prepareDataForBarChart();

        }
        else if(responseObject instanceof String)
            Utility.showToast(Activity_Statistics.this,(String)responseObject);
        else if(responseObject==null)
            Utility.showToast(Activity_Statistics.this, Constants.ERROR_MSG_ANONYMOUS);
    }

    private void prepareDataForBarChart()
    {
        setMontlhyValues();
        setYearlyXValues();
    }

    private void setMontlhyValues()
    {
        monthlyXLabel = new LinkedList<>();
        monthlyXLabel.add("JULY");
        monthlyXLabel.add("AUG");
        monthlyXLabel.add("SEPT");
        monthlyXLabel.add("OCT");
        monthlyXLabel.add("NOV");
        monthlyXLabel.add("DEC");
        monthlyXLabel.add("JAN");
        monthlyXLabel.add("FEB");
        monthlyXLabel.add("MAR");
        monthlyXLabel.add("APR");
        monthlyXLabel.add("MAY");
        monthlyXLabel.add("JUN");

        ArrayList<BarEntry> studentDataSet = new ArrayList<>();
        ArrayList<BarEntry> companyDataSet = new ArrayList<>();
       int val; String key;

        if(statisticsDTO.getMonthCompanyMap()!=null && statisticsDTO.getMonthCompanyMap().size()>0)
        {
            for(int i=1;i<13;i++)
            {
                if(i<7)
                    key=String.valueOf(i+6);
                else
                    key=String.valueOf(i-6);

                val=(statisticsDTO.getMonthCompanyMap().containsKey(key))? statisticsDTO.getMonthCompanyMap().get(key):0;
                companyDataSet.add(new BarEntry(val,i-1));

                val= (statisticsDTO.getMonthStudentMap().containsKey(key))? statisticsDTO.getMonthStudentMap().get(key):0;
                studentDataSet.add(new BarEntry(val,i-1));
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(studentDataSet, "Placements");
        barDataSet1.setColor(barChartColorArray[0]);
        BarDataSet barDataSet2 = new BarDataSet(companyDataSet, "Companies");
        barDataSet2.setColor(barChartColorArray[1]);

        monthlyDataSet = new ArrayList<>();
        monthlyDataSet.add(barDataSet1);
        monthlyDataSet.add(barDataSet2);
    }

    private void setYearlyXValues()
    {
        yearlyXLabel=new ArrayList<>(statisticsDTO.getYearCompanyMap().keySet());

        ArrayList<BarEntry> studentDataSet = new ArrayList<>();
        ArrayList<BarEntry> companyDataSet = new ArrayList<>();

        int val;String key;
        for(int i=0;i<yearlyXLabel.size();i++)
        {
            key=yearlyXLabel.get(i);

            val=(statisticsDTO.getYearCompanyMap().containsKey(key))?statisticsDTO.getYearCompanyMap().get(key):0;
            companyDataSet.add(new BarEntry(val,i));

            val=(statisticsDTO.getYearStudentMap().containsKey(key))?statisticsDTO.getYearStudentMap().get(key):0;
            studentDataSet.add(new BarEntry(val,i));


        }
        BarDataSet barDataSet1 = new BarDataSet(studentDataSet, "Placements");
        barDataSet1.setColor(barChartColorArray[0]);
        BarDataSet barDataSet2 = new BarDataSet(companyDataSet, "Companies");
        barDataSet2.setColor(barChartColorArray[1]);

        yearlyDataSet = new ArrayList<>();
        yearlyDataSet.add(barDataSet1);
        yearlyDataSet.add(barDataSet2);
    }

    private void populateBarChart() {

        BarData barData=null;
        if(statsMode==Constants.STATS_MODE_MONTHLY)
            barData=new BarData(monthlyXLabel,monthlyDataSet);
        else if (statsMode==Constants.STATS_MODE_YEARLY)
            barData=new BarData(yearlyXLabel,yearlyDataSet);

        if(barData.getYValCount()==0)
        {
            barChart.setVisibility(View.GONE);
            Utility.showToast(Activity_Statistics.this,Constants.VALIDATION_MSG_NO_PLACEMENTS);
        }
        else {
            barChart.setData(barData);
            barChart.setVisibleXRange(20);
            barChart.zoomOut();
            barChart.isHorizontalScrollBarEnabled();
            barChart.isDoubleTapToZoomEnabled();
            barChart.setDescription("");
            barChart.fitScreen();
            barChart.animateXY(2000, 2000);
            barChart.invalidate();
        }
    }

    private void saveImage() {
        int millis = Calendar.getInstance().get(Calendar.MILLISECOND);
        if (barLayout.getVisibility() == View.VISIBLE)
            barChart.saveToGallery(statsMode + "_" + millis, 80);
        else
            currentChart.saveToGallery(statsMode + "_" + millis, 80);

        Utility.showToast(Activity_Statistics.this,Constants.SUCCESS_MSG_IMAGE_SAVED);
    }

    private void loadOverallPieChart()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentChart = fragment_overallStatistics.getPieChart(statsMode);
    }
}
