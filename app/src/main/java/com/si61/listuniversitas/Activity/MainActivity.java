package com.si61.listuniversitas.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.si61.listuniversitas.API.APIRequestData;
import com.si61.listuniversitas.API.RetroServer;
import com.si61.listuniversitas.Adapter.AdapterUniversitas;
import com.si61.listuniversitas.Model.ModelResponse;
import com.si61.listuniversitas.Model.ModelUniversitas;
import com.si61.listuniversitas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUniversitas;
    private ProgressBar pbUniversitas;
    private FloatingActionButton fabTambah;
    private RecyclerView.Adapter adUniversitas;
    private RecyclerView.LayoutManager lmUniversitas;
    private List<ModelUniversitas>listUniversitas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUniversitas = findViewById(R.id.rv_universitas);
        pbUniversitas = findViewById(R.id.pb_universitas);
        fabTambah = findViewById(R.id.fab_tambah);

        lmUniversitas = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUniversitas.setLayoutManager(lmUniversitas);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    public  void retrieveUniveristas(){
        pbUniversitas.setVisibility(View.VISIBLE);
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listUniversitas = response.body().getData();

                adUniversitas = new AdapterUniversitas(MainActivity.this, listUniversitas);
                rvUniversitas.setAdapter(adUniversitas);
                adUniversitas.notifyDataSetChanged();
                pbUniversitas.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbUniversitas.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveUniveristas();
    }
}