package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {
    private ArrayList<AlarmTime> alarmTime;
    private LayoutInflater mInflater;
    private Context mContext;
    private Cursor mCursor;
    private ArrayList<AlarmTime> mArrayList;
    //private ItemClickListener mClickListener;
    private AlarmTimeHelper mDatabase;

    public class AlarmViewHolder extends RecyclerView.ViewHolder{

       public TextView tvDate;
       public TextView tvTime;
       public TextView tvAmountTime;
        public AlarmViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView)itemView.findViewById(R.id.tvdate);
            tvTime = (TextView)itemView.findViewById(R.id.tvtime);
            tvAmountTime =(TextView)itemView.findViewById(R.id.tvamounttime);
        }
    }

/* try1
    AlarmListAdapter(Context context, Cursor cursor) {
        //this.mInflater = LayoutInflater.from(context);
        //this.alarmTime = alarmTime;
        mContext = context;
        mCursor = cursor;
    }

 */
public AlarmListAdapter(Context context, ArrayList<AlarmTime> listContacts) {
    this.mContext = context;
    this.alarmTime = listContacts;
    this.mArrayList = listContacts;
    this.mDatabase = new AlarmTimeHelper(context);

}

    // inflates the layout we created that will show one row of data
    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View view = mInflater.inflate(R.layout.alarmtime_item, parent, false);
        //return new ViewHolder(view);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.alarmtime_item, parent, false);
        return new AlarmViewHolder(view);
    }

    // binds the data to the TextView in each row
// this method is indicating which item from the array list is linked to which layout element on the row
    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        //try1
        //AlarmTime alarmTime2 = alarmTime.get(position);
        //holder.tvAddress.setText(person.address);
        //holder.tvName.setText(person.name);

        //holder.tvDate.setText(alarmTime2.date);
        //holder.tvTime.setText(alarmTime2.time);
        //holder.tvAmountTime.setText(alarmTime2.amountTime);

        //try2
        //if(!mCursor.moveToPosition(position)){
        //    return;
        //}
        //String date = mCursor.getString(mCursor.getColumnIndex(AlarmContract.AlarmEntry.DATE));
        //String time = mCursor.getString(mCursor.getColumnIndex(AlarmContract.AlarmEntry.TIME));
        //String amounttime = mCursor.getString(mCursor.getColumnIndex(AlarmContract.AlarmEntry.AmountTIME));

        final AlarmTime alarm = alarmTime.get(position);

        holder.tvDate.setText(alarm.getDate());
        holder.tvTime.setText(alarm.getTime());
        holder.tvAmountTime.setText(alarm.getAmountTime());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        //return mCursor.getCount();
        return alarmTime.size();
    }

    public void sawpCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

    //old
    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView tvName;
        //TextView tvAddress;

        TextView tvDate;
        TextView tvTime;
        TextView tvAmountTime;

        ViewHolder(View itemView) {
            super(itemView);
           // tvName = itemView.findViewById(R.id.tvName);
            //tvAddress = itemView.findViewById(R.id.tvAddress);

            tvDate = itemView.findViewById(R.id.tvdate);
            tvTime = itemView.findViewById(R.id.tvtime);
            tvAmountTime = itemView.findViewById(R.id.tvamounttime);

            //help
            //itemView.setOnClickListener(this);
        }
    }

}
