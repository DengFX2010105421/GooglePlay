package com.dengfx.googleplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.bean.ItemBean;

public class DetailActivity extends AppCompatActivity {

    protected TextView tvItemBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        ItemBean itenBean = (ItemBean) intent.getSerializableExtra("ItenBean");
        tvItemBean = (TextView) findViewById(R.id.tv_itemBean);
        tvItemBean.setText(itenBean.toString());
    }

}
