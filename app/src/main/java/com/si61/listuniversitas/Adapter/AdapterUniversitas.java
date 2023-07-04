package com.si61.listuniversitas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si61.listuniversitas.Activity.DetailActivity;
import com.si61.listuniversitas.Model.ModelUniversitas;
import com.si61.listuniversitas.API.APIRequestData;
import com.si61.listuniversitas.API.RetroServer;
import com.si61.listuniversitas.Activity.MainActivity;
import com.si61.listuniversitas.Activity.TambahActivity;
import com.si61.listuniversitas.Activity.UbahActivity;
import com.si61.listuniversitas.Model.ModelResponse;
import com.si61.listuniversitas.Model.ModelUniversitas;
import com.si61.listuniversitas.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterUniversitas extends RecyclerView.Adapter<AdapterUniversitas.VHUniversitas> {
    private Context ctx;
    private List<ModelUniversitas>listUniversitas;

    public AdapterUniversitas(Context ctx, List<ModelUniversitas> listUniversitas) {
        this.ctx = ctx;
        this.listUniversitas = listUniversitas;
    }

    @NonNull
    @Override
    public VHUniversitas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_universitas,parent, false);
        return new VHUniversitas(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHUniversitas holder, int position) {
        ModelUniversitas MW = listUniversitas.get(position);
        holder.tvId.setText(MW.getId());
        holder.tvNama.setText(MW.getNama());
        holder.tvLokasi.setText(MW.getLokasi());
        holder.tvUrlMap.setText(MW.getUrlmap());
        holder.tvAkreditasi.setText(MW.getAkreditasi());
        holder.tvNo_tlpn.setText(MW.getNo_tlpn());
        holder.tvDetail.setText(MW.getDetail());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nama =listUniversitas.get(holder.getAdapterPosition()).getNama();
//                String detail = listUniversitas.get(holder.getAdapterPosition()).getDetail();
//
//                Intent kirim = new Intent(holder.itemView.getContext(), DetailActivity.class);
//                kirim.putExtra("varNama", nama);
//                kirim.putExtra("varDetail", detail);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listUniversitas.size();
    }

    public class VHUniversitas extends RecyclerView.ViewHolder{

        TextView tvId, tvNama, tvLokasi, tvUrlMap,tvAkreditasi, tvNo_tlpn, tvDetail;


        public VHUniversitas(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvUrlMap = itemView.findViewById(R.id.tv_urlmap);
            tvAkreditasi = itemView.findViewById(R.id.tv_akreditasi);
            tvNo_tlpn = itemView.findViewById(R.id.tv_no_tlpn);
            tvDetail = itemView.findViewById(R.id.tv_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, DetailActivity.class);
                    kirim.putExtra("varNama", tvNama.getText().toString());
                    kirim.putExtra("varDetail", tvDetail.getText().toString());
                    ctx.startActivity(kirim);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Memilih "+tvNama.getText().toString()+"Operasi apa yang akan dilakukan");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                            kirim.putExtra("xUrlmap", tvUrlMap.getText().toString());
                            kirim.putExtra("xAkreditasi", tvAkreditasi.getText().toString());
                            kirim.putExtra("xNo_tlpn", tvNo_tlpn.getText().toString());
                            kirim.putExtra("xDetail", tvDetail.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });
                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvId.getText().toString());

                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }
        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.arddelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveUniveristas();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi Server: "+ t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
