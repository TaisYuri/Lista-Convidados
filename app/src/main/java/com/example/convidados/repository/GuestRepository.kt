package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import kotlin.Exception

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    //singleton  -> controla a quantidade de instancias da classe.
    //Ex: chamada ao banco de dados, apenas pode ser realizada 1 vez. Neste caso, aplica-se Singleton para apenas inicializar uma unica vez (mesmo a classe sendo chamada N vezes)
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            //checa se a var repository NÃO foi inicializada
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context);
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            //tipo ESCRITA para inserir informações no banco de dados
            val db = guestDataBase.writableDatabase

            //transformando boolean em inteiro pois no bd não existe tipo boolean.
            val presence = if (guest.presence) 1 else 0

            //a inserção de dados necessita de um tipo chamado ContentValues.
            // Neste caso, para adicionar o nome da coluna com o valor correspondente.
            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            //3º argumento do update é a clausura WHERE do sql
            //Neste caso, vamos pegar pelo ID apenas (mas o args aceita N valores)
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int) : GuestModel?{
        var guest :GuestModel? = null

        try{
            val db = guestDataBase.readableDatabase


            val projectionColumns = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //Cursor percorre linha a linha da tabela obtendo dados.
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projectionColumns,
                selection,
                args,
                null,
                null,
                null
            )

            //checa se cursor não está nulo ou vazio
            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val name = cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val presence = cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))

                    guest = GuestModel(id, name, presence == 1)  //salva a informação utilizando o modelo

                }
            }
            cursor.close()  //todo cursor precisa ser fechado
        }
        catch (e: Exception){
            return guest
        }
        return guest
    }

    fun getAll() : List<GuestModel>{
        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase


        val projectionColumns = arrayOf(
            DataBaseConstants.GUEST.COLUMNS.ID,
            DataBaseConstants.GUEST.COLUMNS.NAME,
            DataBaseConstants.GUEST.COLUMNS.PRESENCE,
        )

        //Cursor percorre linha a linha da tabela obtendo dados.
        val cursor = db.query(
            DataBaseConstants.GUEST.TABLE_NAME,
            projectionColumns,
            null,
            null,
            null,
            null,
            null
        )

        //checa se cursor não está nulo ou vazio
        if(cursor != null && cursor.count > 0){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.ID))) //obtem o index
                val name = cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                val presence = cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))

                val guest = GuestModel(id, name, presence == 1)  //salva a informação utilizando o modelo
                list.add(guest) //add informação dentro da lista
            }
        }
        cursor.close()  //todo cursor precisa ser fechado
        }
        catch (e: Exception){
            return list
        }
        return list
    }

    fun getWithFilter(typeFilter: Int) : List<GuestModel>{
        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase

            //Outra forma de criar o cursor
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence =" + typeFilter,null)


            //checa se cursor não está nulo ou vazio
            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.ID))) //obtem o index
                    val name = cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val presence = cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))

                    val guest = GuestModel(id, name, presence == 1)  //salva a informação utilizando o modelo
                    list.add(guest) //add informação dentro da lista
                }
            }
            cursor.close()  //todo cursor precisa ser fechado
        }
        catch (e: Exception){
            return list
        }
        return list
    }
}
