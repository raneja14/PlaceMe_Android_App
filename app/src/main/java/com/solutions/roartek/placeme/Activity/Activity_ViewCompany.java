package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.solutions.roartek.placeme.Adapter.Adapter_ViewCompany;
import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ListItemClickCallBack;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Dialog.Dialog_Confirm;
import com.solutions.roartek.placeme.Domain.Entity_Company;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Swathi.K on 09-12-2016.
 */
public class Activity_ViewCompany extends AppCompatActivity implements ListItemClickCallBack,ResponseDelegate,GenericActivityDelegate,OnFragmentCreated{

    private RecyclerView recyclerView;
    private Adapter_ViewCompany adapter_ViewCompany;
    private List<Entity_Company> companyList,deletableCompanyList,tempCompanyList;
    private PreferenceManager preferenceManager;
    private AppManager appManager;
    private Entity_Staff entityStaff;
    private MenuItem deleteItem,saveItem,refreshItem;
    private String CURRENT_OPERATION;
    private int CURRENT_ITEM_POSITION;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);

        initialiseViews();
        setPreferences();
        getObjectFromPreferences();
        onProceed();
    }

    @Override
    public void onItemClick(int position) {

        Entity_Company company = companyList.get(position);
        String companyJson = Utility.convertObjectToJSON(company);
        Intent intent = new Intent(this, Activity_RegisterCompany.class);

        Bundle extras = new Bundle();
        extras.putString(Constants.BUNDLE_COMPANY_KEY, companyJson);
        intent.putExtra(Constants.BUNDLE_KEY, extras);

        CURRENT_ITEM_POSITION = position;
        CURRENT_OPERATION = Constants.OPERATION_UPDATE_COMPANY;
        setPreferences();
        startActivityForResult(intent, MyConfig.REQUEST_CODE_UPDATE_COMPANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== MyConfig.REQUEST_CODE_UPDATE_COMPANY)
        {
            if(resultCode==RESULT_OK)
            {
                Entity_Company entityCompany = (Entity_Company) Utility.convertJSONToObject(Entity_Company.class, data.getStringExtra(Constants.BUNDLE_COMPANY_KEY));
                companyList.set(CURRENT_ITEM_POSITION,entityCompany);
                setAdapter(companyList, false);
                adapter_ViewCompany.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_company, menu);
        deleteItem=menu.findItem(R.id.delete);
        saveItem=menu.findItem(R.id.save);
        refreshItem=menu.findItem(R.id.refresh);
        return true;
    }

    private void setAdapter(List companyList,boolean showCheckBox)
    {
        adapter_ViewCompany = new Adapter_ViewCompany(companyList, this, showCheckBox);
        recyclerView.setAdapter(adapter_ViewCompany);
        adapter_ViewCompany.setItemClickCallBack(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                onDeleteMenuClick();
                return true;
            case R.id.save:
                onTickMenuClick();
                return true;
            case R.id.refresh:
                onRefreshClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onDeleteMenuClick()
    {
        if(deletableCompanyList!=null && deletableCompanyList.size()>0) {

            setAdapter(deletableCompanyList, true);
            adapter_ViewCompany.notifyDataSetChanged();
            displayAppropriateMenuItems(true, false, true,false);
        }
        else
            Utility.showToast(Activity_ViewCompany.this,Constants.VALIDATION_MSG_NO_COMPANY_DELETE);
    }

    private void onRefreshClick() {
        setAdapter(companyList, false);
        adapter_ViewCompany.notifyDataSetChanged();

        displayAppropriateMenuItems(false, true, false,true);
    }

    private void onTickMenuClick() {

        Dialog_Confirm dialogConfirm=new Dialog_Confirm();
        dialogConfirm.show(getFragmentManager(),"dialog");

       /* CURRENT_OPERATION = Constants.OPERATION_DELETE_COMPANY;
        setPreferences();

         ArrayList<Long> compDetailIdList = new ArrayList<>();
        tempCompanyList = new ArrayList<>();

        Iterator<Entity_Company> iterator = deletableCompanyList.iterator();
        while (iterator.hasNext()) {
            Entity_Company compObj = iterator.next();
            if (compObj.isChecked()) {
                compDetailIdList.add(compObj.getCompDetailID());
                tempCompanyList.add(compObj);
                iterator.remove();
            }
        }
        if (compDetailIdList.size() == 0)
            Utility.showToast(Activity_ViewCompany.this, Constants.VALIDATION_MSG_SELECT_COMPANY_DELETE);
        else
            appManager.executeDBCalls(Activity_ViewCompany.this, compDetailIdList, this);*/
    }

    private void displayAppropriateMenuItems(boolean tick,boolean delete,boolean refresh,boolean info) {
        saveItem.setVisible(tick);
        deleteItem.setVisible(delete);
        refreshItem.setVisible(refresh);
    }

    @Override
    public void onPostExecute(Object responseObject) {

        if(responseObject instanceof List)
        {
            companyList= (ArrayList<Entity_Company>) responseObject;
            deletableCompanyList=new ArrayList<>();
            for (Entity_Company company:companyList) {
                if(((Utility.compareDates(Utility.convertStringToDate(company.getDOR()),new Date())>=0) &&
                        (entityStaff.getStaffID()==company.getStaffID() || entityStaff.getStaffID()== MyConfig.adminID)) || (company.getStatus()==0))
                {
                    deletableCompanyList.add(company);
                }
            }
            setAdapter(companyList, false);
        }
        else if(responseObject instanceof Integer  && (int)responseObject==1)
        {
            companyList.removeAll(tempCompanyList);
            setAdapter(companyList, false);
            adapter_ViewCompany.notifyDataSetChanged();
            displayAppropriateMenuItems(false, true, false,true);
        }
        else if(responseObject instanceof String)
            Utility.showToast(Activity_ViewCompany.this,(String)responseObject);
        else if(responseObject==null)
            Utility.showToast(Activity_ViewCompany.this, Constants.ERROR_MSG_ANONYMOUS);

    }

    @Override
    public void onProceed() {
        appManager=new AppManagerImpl();
        Object parameter=null;
        switch (CURRENT_OPERATION)
        {
            case Constants.OPERATION_VIEW_COMPANY:
                parameter=entityStaff.getAppConfigure();
                break;
            case Constants.OPERATION_DELETE_COMPANY:
                parameter=null;
                break;
        }
        appManager.executeDBCalls(Activity_ViewCompany.this, parameter, this);
    }

    @Override
    public void initialiseViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CURRENT_OPERATION=Constants.OPERATION_VIEW_COMPANY;
    }

    @Override
    public void setPreferences() {
        if(preferenceManager==null)
            preferenceManager=new PreferenceManagerImpl(Activity_ViewCompany.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, CURRENT_OPERATION, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
    }

    @Override
    public void onViewCreated(int viewCode) {

        if(viewCode==1)
        {
            CURRENT_OPERATION = Constants.OPERATION_DELETE_COMPANY;
            setPreferences();

            ArrayList<Long> compDetailIdList = new ArrayList<>();
            tempCompanyList = new ArrayList<>();

            Iterator<Entity_Company> iterator = deletableCompanyList.iterator();
            while (iterator.hasNext()) {
                Entity_Company compObj = iterator.next();
                if (compObj.isChecked()) {
                    compDetailIdList.add(compObj.getCompDetailID());
                    tempCompanyList.add(compObj);
                    iterator.remove();
                }
            }
            if (compDetailIdList.size() == 0)
                Utility.showToast(Activity_ViewCompany.this, Constants.VALIDATION_MSG_SELECT_COMPANY_DELETE);
            else
                appManager.executeDBCalls(Activity_ViewCompany.this, compDetailIdList, this);
        }
    }
}
