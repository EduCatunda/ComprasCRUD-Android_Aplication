package com.example.compras_crud.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_CLIENTE = "bd_clientes";

    private static final String TABELA_CLIENTE = "tb_clientes";

    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_EMAIL = "email";

    public BancoDados(Context context) {
        super(context, BANCO_CLIENTE, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CLIENTE + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NOME + " VARCHAR(155), "
                + COLUNA_TELEFONE + " VARCHAR(155), " + COLUNA_EMAIL + " VARCHAR(155))";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    /////CRUD ABAIXO///////

    public void addCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_EMAIL, cliente.getEmail());

        db.insert(TABELA_CLIENTE, null, values);
        db.close();
    }

    public void apagarCLiente(Cliente cliente){

        SQLiteDatabase db = this.getWritableDatabase();
        /*Cliente cd = new Cliente();
        int cd1 = cd.codigo;
        String.valueOf(cliente.codigo);*/

        db.delete(TABELA_CLIENTE, COLUNA_CODIGO + " = ? ", new String[] {String.valueOf(cliente.getCodigo())});

        db.close();


    }

    public Cliente SelecionarCliente(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CLIENTE, new String[] { COLUNA_CODIGO, COLUNA_NOME,
                        COLUNA_TELEFONE, COLUNA_EMAIL}, COLUNA_CODIGO + " = ? ",
                new String[] {String.valueOf(codigo)}, null,null,null,null);

        if(cursor != null ){
            cursor.moveToFirst();
        }
        Cliente cliente = new Cliente(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return cliente;
    }

    public void atualizarCliente(Cliente cliente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_EMAIL, cliente.getEmail());

        db.update(TABELA_CLIENTE, values, COLUNA_CODIGO + " = ? ",
                new String[]{String.valueOf(cliente.getCodigo())});


    }


    public List<Cliente> listaTodosClientes(){
        List<Cliente> listaClientes = new ArrayList<Cliente>();

        String query = "SELECT * FROM " + TABELA_CLIENTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                Cliente cliente = new Cliente();
                cliente.setCodigo(Integer.parseInt(c.getString(0)));
                cliente.setNome(c.getString(1));
                cliente.setTelefone(c.getString(2));
                cliente.setEmail(c.getString(3));

                listaClientes.add(cliente);

            } while (c.moveToNext());

        }

        return listaClientes;
    }








}
