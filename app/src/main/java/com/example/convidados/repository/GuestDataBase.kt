package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DataBaseConstants

//SQLiteOpenHelper -- verifica se o banco de dados já existe. Caso não, é invocado a função onCreate.
class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAMEBD, null, VERSION) {

    companion object {
        private const val NAMEBD = "guestdb"  //nome do banco de dados
        private const val VERSION = 1  //versão do banco de dados
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE" + DataBaseConstants.GUEST.TABLE_NAME + "(" +
                    DataBaseConstants.GUEST.COLUMNS.ID + "integer primary key autoincrement, " +
                    DataBaseConstants.GUEST.COLUMNS.NAME + "text, " +
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE + "integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}