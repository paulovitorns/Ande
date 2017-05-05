package br.com.ande.sqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class GeraBD extends SQLiteOpenHelper {

    private static final String NOME_BANCO  = "ande.db";
    public  static final String TABELA      = "history";
    public  static final String ID          = "_id";
    public  static final String STEPS       = "_steps";
    public  static final String START_TIME  = "_start_time";
    public  static final String FINISH_TIME = "_FINISH_time";
    private static final int VERSAO         = 1;

    public static final String[] dados = new String[]{ID, STEPS, START_TIME, FINISH_TIME};

    public GeraBD(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE "+TABELA+"( " +
                "" + ID + " integer primary key autoincrement," +
                STEPS + " integer," +
                START_TIME + " text," +
                FINISH_TIME + " text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}
