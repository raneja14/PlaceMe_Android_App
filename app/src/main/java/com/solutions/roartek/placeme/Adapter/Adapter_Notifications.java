package com.solutions.roartek.placeme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.ListItemClickCallBack;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.R;

import java.util.List;

/**
 * Created by Swathi.K on 18-12-2016.
 */
public class Adapter_Notifications extends RecyclerView.Adapter<Adapter_Notifications.NotificationHolder> {

    private LayoutInflater inflater;
    private List<Entity_Notification> notificationList;
    private ListItemClickCallBack itemClickCallBack;
    private int[] notificationColor;

    public Adapter_Notifications(List<Entity_Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);
        this.notificationColor = context.getResources().getIntArray(R.array.companyStatusColors);
     }

    public void setItemClickCallBack(final ListItemClickCallBack itemClickCallBack)
    {
        this.itemClickCallBack=itemClickCallBack;
    }

    @Override
    public Adapter_Notifications.NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schema_notifications, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Notifications.NotificationHolder holder, int position) {
        Entity_Notification entityNotification = notificationList.get(position);
        if (entityNotification != null) {
            holder.txt_notifiedCompanyName.setText(entityNotification.getPlacementInfoObj().getCompanyName());
            holder.notifiedBy.setText(entityNotification.getStaffName());
            holder.notifiedStudentNum.setText(entityNotification.getCriteriaObj().getEligibleCount() + " students");
            holder.txt_notifiedDate.setText(Utility.getUIDate(entityNotification.getNotificationDate()));
            holder.notificationImage.setColorFilter(notificationColor[entityNotification.getIsRead()]);
        }

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_notifiedCompanyName, txt_notifiedDate,notifiedBy,notifiedStudentNum;
        private ImageView notificationImage;
        private View container;

        public NotificationHolder(View itemView) {
            super(itemView);

            txt_notifiedCompanyName = (TextView) itemView.findViewById(R.id.notifiedCompanyName);
            txt_notifiedDate = (TextView) itemView.findViewById(R.id.notificationDate);
            notifiedBy = (TextView) itemView.findViewById(R.id.notifiedBy);
            notifiedStudentNum = (TextView) itemView.findViewById(R.id.numOfStudentsNotified);

            notificationImage = (ImageView) itemView.findViewById(R.id.image_notification);
            container = itemView.findViewById(R.id.notificationContainer);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            itemClickCallBack.onItemClick(getAdapterPosition());
            notificationImage.setColorFilter(notificationColor[1]);
        }
    }
}
