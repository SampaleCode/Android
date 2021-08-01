package com.example.loginwithsignup.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE = "test_db.db";
    private static final int VERSION = 1;

    private SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConnect.TABLE_USER + "("
                + DBConnect.C_USERID + " VARCHAR(10), "
                + DBConnect.C_USERNA + " VARCHAR(80), "
                + DBConnect.C_UEMAIL + " VARCHAR(100), "
                + DBConnect.C_PASSWD + " VARCHAR(100),"
                + " PRIMARY KEY (" + DBConnect.C_USERID + ")"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Table
        db.execSQL("DROP TABLE " + DBConnect.TABLE_USER);

        //Create Table
        onCreate(db);

    }

    public boolean validateUserID(String strUserID) {
        boolean validUser = false;
        Cursor cursor;
        try {
            if (!strUserID.equals("")) {
                db = getReadableDatabase();
                cursor = db.query(DBConnect.TABLE_USER, null, DBConnect.C_USERID + "=?",
                        new String[]{strUserID}, null, null, null);
                if (cursor.moveToFirst()) {
                    validUser = true;
                }
                db.close();

            } else {
                validUser = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            validUser = false;
        }
        return validUser;
    }

    public boolean saveUser(ContentValues data) {
        boolean saveBool = true;
        try {
            db = getWritableDatabase();
            if (data != null || !data.isEmpty()) {
                db.insert(DBConnect.TABLE_USER, null, data);
            }
            db.close();

            saveBool = true;

        } catch (Exception e) {
            e.printStackTrace();
            saveBool = false;
        }
        return saveBool;
    }

    public JSONObject getUserData(String strUserID, String strPasswd) {
        JSONObject retObj = new JSONObject();
        db = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = db.query(DBConnect.TABLE_USER, null,
                    DBConnect.C_USERID + "=? AND " + DBConnect.C_PASSWD + "=?",
                    new String[]{strUserID, strPasswd}, null, null, null);
            if (cursor.moveToFirst()) {
                retObj.put(DBConnect.C_USERID, cursor.getString(cursor.getColumnIndex(DBConnect.C_USERID)));
                retObj.put(DBConnect.C_USERNA, cursor.getString(cursor.getColumnIndex(DBConnect.C_USERNA)));
                retObj.put(DBConnect.C_UEMAIL, cursor.getString(cursor.getColumnIndex(DBConnect.C_UEMAIL)));
                retObj.put(DBConnect.C_PASSWD, cursor.getString(cursor.getColumnIndex(DBConnect.C_PASSWD)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObj;
    }

    public boolean updateUser(ContentValues updateData, String strUserID) {
        boolean retUpdate = true;

        try {
            if (updateData.size() > 0) {
                db = getWritableDatabase();
                db.update(DBConnect.TABLE_USER, updateData, DBConnect.C_USERID + "=?",
                        new String[]{strUserID});
                db.close();

                retUpdate = true;
            } else {
                retUpdate = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            retUpdate = false;
        }

        return retUpdate;
    }

    public boolean deleteUser(String strUserID) {
        boolean retDelete = true;

        try {
            db = getWritableDatabase();
            db.delete(DBConnect.TABLE_USER, DBConnect.C_USERID + "=?", new String[]{strUserID});
            db.close();

            retDelete = true;

        } catch (Exception e) {
            e.printStackTrace();
            retDelete = false;
        }
        return retDelete;
    }
}
