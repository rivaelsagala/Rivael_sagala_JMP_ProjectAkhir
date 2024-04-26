package com.example.sqllogin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class InputdataActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText editTextNama, editTextUmur, editTextJenisKelamin, editTextPekerjaan, editTextNoHp, editTextAlamat;
    Button buttonTambah;
    Button buttonUpdate;
    Button buttonHapus;

    Button buttonClear;

    ListView listViewAnggota;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdata);

        databaseHelper = new DatabaseHelper(this);

        editTextNama = findViewById(R.id.editTextNama);
        editTextUmur = findViewById(R.id.editTextUmur);
        editTextJenisKelamin = findViewById(R.id.editTextJenisKelamin);
        editTextPekerjaan = findViewById(R.id.editTextPekerjaan);
        editTextNoHp = findViewById(R.id.editTextNoHp);
        editTextAlamat = findViewById(R.id.editTextAlamat);

        buttonTambah = findViewById(R.id.buttonTambah);
        listViewAnggota = findViewById(R.id.listViewAnggota);
        buttonHapus = findViewById(R.id.buttonHapus);
        buttonClear = findViewById(R.id.buttonClear);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = editTextNama.getText().toString();
                int umur = Integer.parseInt(editTextUmur.getText().toString());
                String jenisKelamin = editTextJenisKelamin.getText().toString();
                String pekerjaan = editTextPekerjaan.getText().toString();
                String noHp = editTextNoHp.getText().toString();
                String alamat = editTextAlamat.getText().toString();

                boolean inserted = databaseHelper.insertAnggota(nama, umur, jenisKelamin, pekerjaan, noHp, alamat);
                if (inserted) {
                    Toast.makeText(InputdataActivity.this, "Anggota ditambahkan", Toast.LENGTH_SHORT).show();
                    loadAnggotaList();

                    editTextNama.setText("");
                    editTextUmur.setText("");
                    editTextJenisKelamin.setText("");
                    editTextPekerjaan.setText("");
                    editTextNoHp.setText("");
                    editTextAlamat.setText("");
                } else {
                    Toast.makeText(InputdataActivity.this, "Gagal menambahkan anggota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listViewAnggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                    int umur = cursor.getInt(cursor.getColumnIndexOrThrow("umur"));
                    String jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin"));
                    String pekerjaan = cursor.getString(cursor.getColumnIndexOrThrow("pekerjaan"));
                    String noHp = cursor.getString(cursor.getColumnIndexOrThrow("nohp"));
                    String alamat = cursor.getString(cursor.getColumnIndexOrThrow("alamat"));

                    editTextNama.setText(nama);
                    editTextUmur.setText(String.valueOf(umur));
                    editTextJenisKelamin.setText(jenisKelamin);
                    editTextPekerjaan.setText(pekerjaan);
                    editTextNoHp.setText(noHp);
                    editTextAlamat.setText(alamat);
                }
            }
        });



        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = editTextNama.getText().toString();
                int umur = Integer.parseInt(editTextUmur.getText().toString());
                String jenisKelamin = editTextJenisKelamin.getText().toString();
                String pekerjaan = editTextPekerjaan.getText().toString();
                String noHp = editTextNoHp.getText().toString();
                String alamat = editTextAlamat.getText().toString();


                int position = listViewAnggota.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {

                    Cursor cursor = (Cursor) adapter.getItem(position);
                    if (cursor != null) {

                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                        // Panggil fungsi updateAnggota() dari DatabaseHelper
                        boolean updated = databaseHelper.updateAnggota(id, nama, umur, jenisKelamin, pekerjaan, noHp, alamat);
                        if (updated) {
                            Toast.makeText(InputdataActivity.this, "Anggota diperbarui", Toast.LENGTH_SHORT).show();
                            loadAnggotaList(); // Memuat ulang daftar anggota setelah pembaruan
                            // Mengosongkan EditText setelah data diperbarui
                            editTextNama.setText("");
                            editTextUmur.setText("");
                            editTextJenisKelamin.setText("");
                            editTextPekerjaan.setText("");
                            editTextNoHp.setText("");
                            editTextAlamat.setText("");
                        } else {
                            Toast.makeText(InputdataActivity.this, "Gagal memperbarui anggota", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(InputdataActivity.this, "Pilih anggota yang ingin diperbarui", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan posisi item yang dipilih di ListView
                int position = listViewAnggota.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    // Dapatkan Cursor dari adapter
                    Cursor cursor = (Cursor) adapter.getItem(position);
                    if (cursor != null) {
                        // Dapatkan ID dari item yang dipilih
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                        boolean deleted = databaseHelper.deleteAnggota(id);
                        if (deleted) {
                            Toast.makeText(InputdataActivity.this, "Anggota dihapus", Toast.LENGTH_SHORT).show();
                            loadAnggotaList(); // Memuat ulang daftar anggota setelah penghapusan
                            // Mengosongkan EditText setelah data dihapus
                            editTextNama.setText("");
                            editTextUmur.setText("");
                            editTextJenisKelamin.setText("");
                            editTextPekerjaan.setText("");
                            editTextNoHp.setText("");
                            editTextAlamat.setText("");                        } else {
                            Toast.makeText(InputdataActivity.this, "Gagal menghapus anggota", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(InputdataActivity.this, "Pilih anggota yang ingin dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengosongkan semua EditText
                editTextNama.setText("");
                editTextUmur.setText("");
                editTextJenisKelamin.setText("");
                editTextPekerjaan.setText("");
                editTextNoHp.setText("");
                editTextAlamat.setText("");
            }
        });




        loadAnggotaList();
    }

    private void loadAnggotaList() {
        Cursor cursor = databaseHelper.getAllAnggota();
        String[] fromColumns = {"nama", "umur", "jenis_kelamin", "pekerjaan", "nohp", "alamat"};
        int[] toViews = {R.id.nama, R.id.umur, R.id.jenis_kelamin, R.id.pekerjaan, R.id.nohp, R.id.alamat};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item_anggota, cursor, fromColumns, toViews, 0);
        listViewAnggota.setAdapter(adapter);
    }
}
