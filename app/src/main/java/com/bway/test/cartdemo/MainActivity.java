package com.bway.test.cartdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url = "http://result.eolinker.com/iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=one";
    private List<Data.DataBean> mList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private CheckBox mCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

    }

    private void initData() {
        RequestParams param = new RequestParams(url);
        x.http().get(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Gson gson = new Gson();
                    Data data = gson.fromJson(result, Data.class);
                    adapter = new RecyclerViewAdapter( data.getData(),MainActivity.this);
                    mRecyclerView.setAdapter(adapter);

                } else {
                    onError(new NullPointerException("返回数据为空"), false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, "返回数据为空", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {
        mList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckbox.isChecked()){
                    mCheckbox.setChecked(true);
                    adapter.selectedAll();
                }else{
                    mCheckbox.setChecked(false);
                    adapter.cancleAll();
                }
            }
        });
    }
    public void setCb(boolean bool) {
        mCheckbox.setChecked(bool);
    }
}
