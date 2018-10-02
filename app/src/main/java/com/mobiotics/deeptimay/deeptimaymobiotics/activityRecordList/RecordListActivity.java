package com.mobiotics.deeptimay.deeptimaymobiotics.activityRecordList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mobiotics.deeptimay.deeptimaymobiotics.R;
import com.mobiotics.deeptimay.deeptimaymobiotics.activityExoPlayer.ExoPlayerActivity;
import com.mobiotics.deeptimay.deeptimaymobiotics.adapters.RecordListRv;
import com.mobiotics.deeptimay.deeptimaymobiotics.database.MobioticsDb;
import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;
import com.mobiotics.deeptimay.deeptimaymobiotics.utils.MyUtilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecordListActivity extends AppCompatActivity implements GetDataRecodrs.View {

    @BindView(R.id.rvRecords)
    RecyclerView rvRecords;

    RecordListRv recordListRv;
    int position = 0;
    String FETCH_URL = "https://interview-e18de.firebaseio.com/media.json?print=pretty/";
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list_activity);
        ButterKnife.bind(this);
        MyUtilities.setSharedPref(RecordListActivity.this, "dashboard", "dashboard");
        mPresenter = new Presenter(this);
    }

    private void showRecords(ArrayList<Records> responseArrayList) {

        recordListRv = new RecordListRv(responseArrayList, RecordListActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RecordListActivity.this);
        rvRecords.setLayoutManager(mLayoutManager);
        rvRecords.setAdapter(recordListRv);
        recordListRv.notifyDataSetChanged();
        rvRecords.getLayoutManager().scrollToPosition(position);

        recordListRv.setOnItemClickListener(new RecordListRv.MyClickListener() {
            @Override
            public void onItemClick(Records selectedData, int pos) {
                position = pos;
                Intent intent = new Intent(RecordListActivity.this, ExoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedData", selectedData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGetDataSuccess(String message, ArrayList<Records> allRecords) {
        showRecords(allRecords);
    }

    @Override
    public void onGetDataFailure(String message) {
        Log.d("Status", message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobioticsDb mobioticsDb = new MobioticsDb(RecordListActivity.this);
        if (mobioticsDb.getRecordCount() < 1) {
            mPresenter.getDataFromURL(getApplicationContext(), FETCH_URL);
        } else
            showRecords(mobioticsDb.getAllRecords());
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
