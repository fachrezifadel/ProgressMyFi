package android.lalita.com.myfi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "keuangan.db";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Transaction.SQL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Transaction.SQL_DELETE);
        onCreate(db);
    }
    public void insertTransaction(String name, int type, int amount, String description){
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transaction.COL_NAME, name);
        values.put(Transaction.COL_TYPE, type);
        values.put(Transaction.COL_AMOUNT, amount);
        values.put(Transaction.COL_DESCRIPTION, description);
        db.insert(Transaction.TABLE_NAME, null, values);
    }
    public List<Transaction> getTransactions(){
        db = getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Transaction.TABLE_NAME+
                " order by "+Transaction._ID, null);
        Transaction newTrans;
        try{
            while (cursor.moveToNext()){
                newTrans = new Transaction(Long.toString(cursor.getLong(0)), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
                transactions.add(newTrans);
            }
        } finally {
            cursor.close();
        }
        return transactions;
    }
    public void deleteTransaction(Transaction transaction){
        db = getWritableDatabase();
        db.delete(Transaction.TABLE_NAME, Transaction._ID+" = ?",
                new String[]{transaction.getId()});
    }
}

