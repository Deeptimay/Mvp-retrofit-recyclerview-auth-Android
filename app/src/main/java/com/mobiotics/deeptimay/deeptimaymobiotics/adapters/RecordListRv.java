package com.mobiotics.deeptimay.deeptimaymobiotics.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiotics.deeptimay.deeptimaymobiotics.R;
import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecordListRv extends RecyclerView.Adapter<RecordListRv.DataObjectHolder> {

    private static String LOG_TAG = "RecordListRv";
    private static MyClickListener myClickListener;
    private static ArrayList<Records> mDataset = new ArrayList<>();
    private Context context;

    public RecordListRv(ArrayList<Records> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        RecordListRv.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.records_single_row, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        final Records records = mDataset.get(holder.getAdapterPosition());

        holder.name.setText(records.getTitle());
        holder.description.setText(records.getDescription());

        if (!TextUtils.isEmpty(records.getThumb()))
            Picasso.with(context).load(records.getThumb()).placeholder(R.mipmap.ic_launcher).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onItemClick(records, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(Records selectedData, int position);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView image;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            name = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}