package com.si61.listuniversitas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si61.listuniversitas.API.APIRequestData;
import com.si61.listuniversitas.API.RetroServer;
import com.si61.listuniversitas.Model.ModelResponse;
import com.si61.listuniversitas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etLokasi, etUrlmap, etAkreditasi, etNo_tlpn, etDetail;
    private Button btnSimpan;
    private String  nama, lokasi, urlmap, akreditasi,  no_tlpn, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama = findViewById(R.id.et_nama);
        etLokasi = findViewById(R.id.et_lokasi);
        etUrlmap = findViewById(R.id.et_urlmap);
        etAkreditasi = findViewById(R.id.et_akreditasi);
        etNo_tlpn = findViewById(R.id.et_no_tlpn);
        etDetail = findViewById(R.id.et_detail);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                lokasi = etLokasi.getText().toString();
                urlmap = etUrlmap.getText().toString();
                akreditasi = etAkreditasi.getText().toString();
                no_tlpn = etNo_tlpn.getText().toString();
                detail = etDetail.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong");
                }
                else if(lokasi.trim().isEmpty()){
                    etLokasi.setError("Lokasi Tidak Boleh Kosong");
                }
                else if(urlmap.trim().isEmpty()){
                    etUrlmap.setError("UrlMap Tidak Boleh Kosong");
                }
                else if(akreditasi.trim().isEmpty()){
                    etAkreditasi.setError("Akreditasi Tidak Boleh Kosong");
                }
                else if(no_tlpn.trim().isEmpty()){
                    etNo_tlpn.setError("No Telepon Tidak Boleh Kosong");
                }
                else if(detail.trim().isEmpty()){
                    etDetail.setError("Detail Universitas Tidak Boleh Kosong");
                }
                else{
                    prosesSimpan();
                }
            }
        });
    }
    private void  prosesSimpan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, lokasi, urlmap, akreditasi, no_tlpn, detail);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}