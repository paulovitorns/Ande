package br.com.ande.sqlLite.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ande.sqlLite.GeraBD;
import br.com.ande.sqlLite.entity.Histoty;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ControllerBD {

    private SQLiteDatabase db;
    private GeraBD banco;

    public ControllerBD(Context context){
        banco = new GeraBD(context);
    }

    public boolean insereDados(Histoty histoty){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(GeraBD.ID, histoty.getId());
        valores.put(GeraBD.STEPS, histoty.getSteps());
        valores.put(GeraBD.START_TIME, histoty.getStartTime());
        valores.put(GeraBD.FINISH_TIME, histoty.getFinishTime());
        valores.put(GeraBD.DURATION_TIME, histoty.getDurationTime());

        resultado = db.insert(GeraBD.TABELA, null, valores);
        db.close();

        if(resultado == -1)
            return false;
        else
            return true;
    }

    public Cursor carregaHistory(int id){

        String where = GeraBD.ID + "=" + id;

        Cursor cursor; String[] campos = banco.dados;
        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }


    public Cursor carregaHistories(){

        Cursor cursor; String[] campos = banco.dados;
        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public List<Object[]> cursorToTableRows(Cursor cursor){
        List<Object[]> result = new ArrayList<>(cursor.getCount());

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Object[] tableRow = new Object[cursor.getColumnCount()];
            for (int i = 0; i<cursor.getColumnNames().length; i++){

                int columnIndex = cursor.getColumnIndex(cursor.getColumnName(i));
                String columnValue = cursor.getString(columnIndex);
                tableRow[i] = columnValue;
            }
            result.add(tableRow);
            cursor.moveToNext();
        }

        cursor.close();

        return result;
    }

    public List<Histoty> getHistories(){
        List<Histoty> histoties = new ArrayList<>();
        Cursor cursor = carregaHistories();
        List<Object[]> objects = cursorToTableRows(cursor);

        System.out.println("Teste: " + objects.size());

        for (Object[] row : objects){
            int i = 0;
            Histoty histoty = new Histoty(
                    Integer.parseInt(row[i++].toString()),
                    Integer.parseInt(row[i++].toString()),
                    row[i++].toString(),
                    row[i++].toString(),
                    row[i++].toString()
            );

            histoties.add(histoty);
        }

        return histoties;
    }


    public void delete(int id){
        String where = GeraBD.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(GeraBD.TABELA, where, null);
        db.close();
    }

    public void deleteAll() {

        db = banco.getReadableDatabase();
        db.delete(GeraBD.TABELA, null, null);
    }

}
