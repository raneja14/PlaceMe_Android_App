package com.solutions.roartek.placeme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.ListItemClickCallBack;
import com.solutions.roartek.placeme.Domain.Entity_Company;
import com.solutions.roartek.placeme.R;

import java.util.List;

/**
 * Created by Swathi.K on 17-12-2016.
 */
public class Adapter_ViewCompany extends RecyclerView.Adapter<Adapter_ViewCompany.RecyclerHolder> {
    private List<Entity_Company> companyList;
    private LayoutInflater inflater;
    private String[] typeArray;
    private int[] statusColor;
    private int visibleMode;
    private ListItemClickCallBack itemClickCallBack;

    public void setItemClickCallBack(final ListItemClickCallBack itemClickCallBack)
    {
        this.itemClickCallBack=itemClickCallBack;
    }

    public Adapter_ViewCompany(List<Entity_Company> companyList, Context context, boolean showCheckBox) {
        this.companyList = companyList;
        this.inflater = LayoutInflater.from(context);
        this.typeArray=  context.getResources().getStringArray(R.array.type_of_rec_array);
        this.statusColor = context.getResources().getIntArray(R.array.companyStatusColors);
        this.visibleMode =(showCheckBox?View.VISIBLE:View.GONE);

    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schema_view_company, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        Entity_Company companyObj = companyList.get(position);
        if(companyObj!=null) {
            holder.nameText.setText(companyObj.getCompName());
            holder.dateText.setText(Utility.getUIDate(companyObj.getDOR()));
            holder.typeText.setText(typeArray[companyObj.getType()]);
            holder.hostText.setText(companyObj.getHost());
            holder.companyLetterText.setText(companyObj.getCompName().length()>0? companyObj.getCompName().substring(0,1):"");
            holder.statusImage.setColorFilter(statusColor[companyObj.getStatus()]);
            holder.checkBox.setVisibility(visibleMode);
        }
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameText,dateText,typeText,hostText,companyLetterText;
        private ImageView statusImage;
        private View container;
        private CheckBox checkBox;

        public RecyclerHolder(View itemView) {
            super(itemView);

            nameText=(TextView)itemView.findViewById(R.id.viewCompanyName);
            dateText=(TextView)itemView.findViewById(R.id.viewCompanyDate);
            hostText=(TextView)itemView.findViewById(R.id.viewCompanyHost);
            typeText=(TextView)itemView.findViewById(R.id.viewCompanyType);
            companyLetterText = (TextView) itemView.findViewById(R.id.viewCompany_txt_companyLetter);

            statusImage = (ImageView) itemView.findViewById(R.id.image_companyStatus);
            container= itemView.findViewById(R.id.viewCompanyConatiner);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            container.setOnClickListener(this);
            checkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(visibleMode==View.GONE)
            {
                if(v.getId()==R.id.viewCompanyConatiner)
                {
                    itemClickCallBack.onItemClick(getAdapterPosition());
                }
            }

            if(v.getId()==R.id.checkbox)
            {
                companyList.get(getAdapterPosition()).setIsChecked(((CheckBox)v).isChecked());
            }
        }
    }

}
