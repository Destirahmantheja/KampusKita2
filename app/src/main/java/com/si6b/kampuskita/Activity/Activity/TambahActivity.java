package com.si6b.kampuskita.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si6b.kampuskita.Activity.API.APIRequestData;
import com.si6b.kampuskita.Activity.API.RetroServer;
import com.si6b.kampuskita.Activity.Model.ModelResponse;
import com.si6b.kampuskita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etKota, etAlamat;
    private Button btnTambah;
    private String nama, kota, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etKota = findViewById(R.id.et_kota);
        etAlamat = findViewById(R.id.et_alamat);
        btnTambah = findViewById(R.id.btn_Tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                kota = etKota.getText().toString();
                alamat = etAlamat.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama tidak boleh kosong");
                }
                else if(kota.trim().isEmpty()){
                    etKota.setError("Kota tidak boleh kosong");

                }
                else if(alamat.trim().isEmpty()){
                    etAlamat.setError("Alamat tidak boleh kosong");
                }
                else{
                    tambahKampus();
                }
            }
        });
    }

    private void tambahKampus(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, kota, alamat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "kode : " + kode +"Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Error:Gagal menghubungi server!", Toast.LENGTH_SHORT).show();
                Log.d("Disini", "Errornya itu: " + t.getMessage());
            }
        });
    }
}