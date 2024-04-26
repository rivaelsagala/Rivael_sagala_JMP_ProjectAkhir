package com.example.sqllogin;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LihatdataActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView listViewAnggota;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatdata);

        databaseHelper = new DatabaseHelper(this);
        listViewAnggota = findViewById(R.id.listViewAnggota);
        loadAnggotaList(); // Load member data when the activity starts

        // Adding a listener to handle clicks on the ListView items
        listViewAnggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor containing data from the clicked item
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    // Extract data from the cursor
                    String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                    int umur = cursor.getInt(cursor.getColumnIndexOrThrow("umur"));
                    String jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin"));
                    String pekerjaan = cursor.getString(cursor.getColumnIndexOrThrow("pekerjaan"));
                    String noHp = cursor.getString(cursor.getColumnIndexOrThrow("nohp"));
                    String alamat = cursor.getString(cursor.getColumnIndexOrThrow("alamat"));

                    // Create text to display in the dialog
                    String detailData = "Nama: " + nama + "\n" +
                            "Umur: " + umur + "\n" +
                            "Jenis Kelamin: " + jenisKelamin + "\n" +
                            "Pekerjaan: " + pekerjaan + "\n" +
                            "No. HP: " + noHp + "\n" +
                            "Alamat: " + alamat;

                    // Create and show the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(LihatdataActivity.this);
                    builder.setTitle("Detail Anggota")
                            .setMessage(detailData)
                            .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dialog closed, do nothing
                                }
                            })
                            .show();
                }
            }
        });
    }
    // Load the list of members
    private void loadAnggotaList() {
        Cursor cursor = databaseHelper.getAllAnggota();
        String[] fromColumns = {"nama", "umur", "jenis_kelamin", "pekerjaan", "nohp", "alamat"};
        int[] toViews = {R.id.nama, R.id.umur, R.id.jenis_kelamin, R.id.pekerjaan, R.id.nohp, R.id.alamat};
        adapter = new SimpleCursorAdapter(this, R.layout.list_anggota, cursor, fromColumns, toViews, 0);
        listViewAnggota.setAdapter(adapter);
    }
}
