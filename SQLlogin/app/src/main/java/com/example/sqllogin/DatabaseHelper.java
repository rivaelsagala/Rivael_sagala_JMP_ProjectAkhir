    package com.example.sqllogin;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String databaseName = "pptsb.db";

        public DatabaseHelper(Context context) {
            super(context, databaseName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE users (email TEXT PRIMARY KEY, password TEXT)");
            db.execSQL("CREATE TABLE anggota (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nama TEXT," +
                    "umur INTEGER," +
                    "jenis_kelamin TEXT," +
                    "pekerjaan TEXT," +
                    "nohp TEXT," +
                    "alamat TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS anggota");
            onCreate(db);
        }

        // User Management Functions
        public boolean insertData(String email, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            long result = db.insert("users", null, contentValues);
            return result != -1;
        }

        public boolean checkEmail(String email) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
            return cursor.getCount() > 0;
        }

        public boolean checkEmailPassword(String email, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
            return cursor.getCount() > 0;
        }

        // CRUD Operations for Anggota
        public boolean insertAnggota(String nama, int umur, String jenisKelamin, String pekerjaan, String noHp, String alamat) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("nama", nama);
            contentValues.put("umur", umur);
            contentValues.put("jenis_kelamin", jenisKelamin);
            contentValues.put("pekerjaan", pekerjaan);
            contentValues.put("nohp", noHp);
            contentValues.put("alamat", alamat);
            long result = db.insert("anggota", null, contentValues);
            return result != -1;
        }

        public Cursor getAllAnggota() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("SELECT id as _id, nama, umur, jenis_kelamin, pekerjaan, nohp, alamat FROM anggota", null);
        }



        public boolean updateAnggota(int id, String nama, int umur, String jenisKelamin, String pekerjaan, String noHp, String alamat) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nama", nama);
            contentValues.put("umur", umur);
            contentValues.put("jenis_kelamin", jenisKelamin);
            contentValues.put("pekerjaan", pekerjaan);
            contentValues.put("nohp", noHp);
            contentValues.put("alamat", alamat);
            int result = db.update("anggota", contentValues, "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        }

        public boolean deleteAnggota(long id) {
            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete("anggota", "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        }

    }


