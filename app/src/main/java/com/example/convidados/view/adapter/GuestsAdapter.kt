package com.example.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestsBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.view.viewHolder.GuestViewHolder

//ViewHolder = referencia dos elementos que estarão dentro da lista
class GuestsAdapter: RecyclerView.Adapter<GuestViewHolder>() {

    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener: OnGuestListener

    //onCreate = cria o layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
         val item = RowGuestsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(item, listener)
    }

    //onBind = cola os itens dentro do layout
    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
       holder.bind(guestList[position])
    }

    //getItem = quantidade de itens
    override fun getItemCount(): Int {
       return guestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener){
        listener = guestListener
    }
}