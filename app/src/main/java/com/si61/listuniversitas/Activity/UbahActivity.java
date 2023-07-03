package com.si61.listuniversitas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etLokasi, etUrlmap, etAkreditasi, etNo_tlpn;
    private Button btnSimpan;
    private String  nama, lokasi, urlmap, akreditasi, no_tlpn;
    private  String yId, yNama, yLokasi, yUrlmap, yAkreditasi, yNo_tlpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etLokasi = findViewById(R.id.et_lokasi);
        etUrlmap = findViewById(R.id.et_urlmap);
        etAkreditasi = findViewById(R.id.et_akreditasi);
        etNo_tlpn = findViewById(R.id.et_no_tlpn);
        btnSimpan = findViewById(R.id.btn_simpan);

        Intent tangkap = getIntent();
        yId = tangkap.getStringExtra("xId");
        yNama = tangkap.getStringExtra("xNama");
        yLokasi = tangkap.getStringExtra("xLokasi");
        yUrlmap = tangkap.getStringExtra("xUrlmap");
        yAkreditasi = tangkap.getStringExtra("xAkreditasi");
        yNo_tlpn = tangkap.getStringExtra("xNo_tlpn");

        etNama.setText(yNama);
        etLokasi.setText(yLokasi);
        etUrlmap.setText(yUrlmap);
        etAkreditasi.setText(yAkreditasi);
        etNo_tlpn.setText(yNo_tlpn);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                lokasi = etLokasi.getText().toString();
                urlmap = etUrlmap.getText().toString();
                akreditasi = etAkreditasi.getText().toString();
                no_tlpn = etNo_tlpn.getText().toString();


                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Universitas Tidak Boleh Kosong");
                }
                else if(lokasi.trim().isEmpty()){
                    etLokasi.setError("Lokasi Universitas Tidak Boleh Kosong");
                }
                else if(urlmap.trim().isEmpty()){
                    etUrlmap.setError("UrlMap Universitas Tidak Boleh Kosong");
                }
                else if(akreditasi.trim().isEmpty()){
                    etAkreditasi.setError("Akreditasi Universitas Tidak Boleh Kosong");
                }
                else if(no_tlpn.trim().isEmpty()){
                    etNo_tlpn.setError("No telepon Universitas Tidak Boleh Kosong");
                }
                else{
                    prossesUbah();

                }
            }
        });
    }

    private void  prossesUbah(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, nama, lokasi, urlmap, akreditasi, no_tlpn);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}