package gabrielaMatheus.com.catDiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GatoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private final String TABELA = "Gatos";
    private static final String DATABASE = "DadosGato";

    public GatoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + " nome TEXT NOT NULL, "
                + " idade TEXT, "
                + " sexo TEXT, "
                + " raca TEXT, "
                + " peso TEXT, "
                + " foto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<GatoInfo> getList(String order){
        List<GatoInfo> gatos = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " ORDER BY nome " +
                order + ";", null);

        while(cursor.moveToNext()){
            GatoInfo c = new GatoInfo();

            c.setId(cursor.getLong(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setIdade(cursor.getString(cursor.getColumnIndex("idade")));
            c.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
            c.setRaca(cursor.getString(cursor.getColumnIndex("raca")));
            c.setPeso(cursor.getString(cursor.getColumnIndex("peso")));
            c.setFoto(cursor.getString(cursor.getColumnIndex("foto")));

            gatos.add(c);
        }

        cursor.close();

        return gatos;
    }

    public void inserirGato(GatoInfo c){
        ContentValues values = new ContentValues();

        values.put("nome", c.getNome());
        values.put("idade", c.getIdade());
        values.put("sexo", c.getSexo());
        values.put("raca", c.getRaca());
        values.put("peso", c.getPeso());
        values.put("foto", c.getFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public void alteraGato(GatoInfo c){
        ContentValues values = new ContentValues();

        values.put("id", c.getId());
        values.put("nome", c.getNome());
        values.put("idade", c.getIdade());
        values.put("sexo", c.getSexo());
        values.put("raca", c.getRaca());
        values.put("peso", c.getPeso());
        values.put("foto", c.getFoto());

        String[] idParaSerAlterado = {c.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", idParaSerAlterado);
    }

    public void apagarGato(GatoInfo c){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {c.getId().toString()};
        db.delete(TABELA, "id=?", args);
    }
}

