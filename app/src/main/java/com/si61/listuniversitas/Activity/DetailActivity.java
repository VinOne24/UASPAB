package com.si61.listuniversitas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.si61.listuniversitas.R;

public class DetailActivity extends AppCompatActivity {

    private TextView tvNama, tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvDetail = findViewById(R.id.tv_detail);

        Intent terima = getIntent();
        String nama = terima.getStringExtra("varNama");
        String detail = terima.getStringExtra("varDetail");

        tvNama.setText(nama);
        tvDetail.setText(detail);
    }
}