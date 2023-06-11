package com.example.convidados.view.viewHolder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestsBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestsBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(item: GuestModel) {
        bind.textName.text = item.name

        bind.textName.setOnClickListener{
            listener.onClick(item.id)
        }

        bind.textName.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("sim"
                ) { dialog, which ->
                    listener.onDelete(item.id)
                }
                .setNegativeButton("Não", null)
                .create().show()


            true
        }


    }
}
