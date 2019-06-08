package com.antonio.fatecquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDAO extends SQLiteOpenHelper {

    public QuizDAO(Context context) {
        super(context, "Quiz", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCodigo = "CREATE TABLE IF NOT EXISTS Pergunta (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pergunta TEXT, " +
                "alternativaA TEXT, " +
                "alternativaB TEXT, " +
                "alternativaC TEXT, " +
                "alternativaD TEXT, " +
                "resposta TEXT, " +
                "alternativaCorreta TEXT NOT NULL);";

        db.execSQL(sqlCodigo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void cadastraPergunta(Pergunta pergunta) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = PegaDadosDaPergunta(pergunta);
        db.insert("Pergunta", null, dados);
    }

    private ContentValues PegaDadosDaPergunta(Pergunta pergunta) {
        ContentValues dados = new ContentValues();
        dados.put("pergunta", pergunta.getPergunta());
        dados.put("alternativaA", pergunta.getAlternativaA());
        dados.put("alternativaB", pergunta.getAlternativaB());
        dados.put("alternativaC", pergunta.getAlternativaC());
        dados.put("alternativaD", pergunta.getAlternativaD());
        dados.put("resposta", pergunta.getResposta());
        dados.put("alternativaCorreta", pergunta.getAlternativaCorreta());

        return dados;
    }

    public List<Pergunta> buscaPerguntas() {
        String sql = "SELECT * FROM Pergunta";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Pergunta> perguntas = populaPerguntas(c);

        return perguntas;
    }

    private List<Pergunta> populaPerguntas(Cursor cursor) {
        List<Pergunta> perguntas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Pergunta pergunta = new Pergunta();
            pergunta.setId(cursor.getInt(cursor.getColumnIndex("id")));
            pergunta.setPergunta(cursor.getString(cursor.getColumnIndex("pergunta")));
            pergunta.setAlternativaA(cursor.getString(cursor.getColumnIndex("alternativaA")));
            pergunta.setAlternativaB(cursor.getString(cursor.getColumnIndex("alternativaB")));
            pergunta.setAlternativaC(cursor.getString(cursor.getColumnIndex("alternativaC")));
            pergunta.setAlternativaD(cursor.getString(cursor.getColumnIndex("alternativaD")));
            pergunta.setAlternativaCorreta(cursor.getString(cursor.getColumnIndex("alternativaCorreta")));
            pergunta.setResposta(cursor.getString(cursor.getColumnIndex("resposta")));

            perguntas.add(pergunta);
        }
        return perguntas;
    }

    public void altera(Pergunta pergunta) {
        ContentValues dados = PegaDadosDaPergunta(pergunta);
        SQLiteDatabase dao = getWritableDatabase();

        String[] params = {pergunta.getId() + ""};
        dao.update("Pergunta", dados, "id=?", params);
    }

    public void resetarBanco() {
        String comando = "DELETE FROM Pergunta";
        SQLiteDatabase dao = getWritableDatabase();
        dao.execSQL(comando);
    }

    public void resetarPerguntas() {
        String comando = "UPDATE Pergunta SET resposta=null";
        SQLiteDatabase dao = getWritableDatabase();
        dao.execSQL(comando);
    }

}
