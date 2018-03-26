package com.solutions.roartek.placeme.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.solutions.roartek.placeme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swathi.k on 09-12-2016.
 */
public class Fragment_Branches extends Fragment {

    private List<String> branchList;
    private ArrayAdapter<String> branchAdapter;
    private ListView branchListView;
    private View view;

    public Fragment_Branches() {
    }

    public Fragment_Branches(List<String> branchList) {
        this.branchList = branchList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_branch, container, false);
        branchListView = (ListView) view.findViewById(R.id.branch_listView);
        branchAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, branchList);
        branchListView.setAdapter(branchAdapter);
        return view;
    }

    public List<String> getCheckedItems() {
        List<String> branchList = new ArrayList<>();
        SparseBooleanArray sparseBooleanArray = branchListView.getCheckedItemPositions();
        for (int i = 0; i < branchListView.getCount(); i++) {
            if (sparseBooleanArray.get(i) == true) {
                branchList.add("'" + branchListView.getItemAtPosition(i).toString() + "'");
            }
        }
        return branchList;
    }

    public void checkAll() {
        int itemCount = branchListView.getCount();
        for (int i = 0; i < itemCount; i++) {
            branchListView.setItemChecked(i, true);
        }
    }

    public void unCheckAll() {
        int itemCount = branchListView.getCount();
        for (int i = 0; i < itemCount; i++) {
            branchListView.setItemChecked(i, false);
        }
    }
}