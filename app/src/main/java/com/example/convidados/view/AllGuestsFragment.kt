package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle? ): View {
        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //RECYCLERVIEW - LAYOUT
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        //RECYCLERVIEW - ADAPTER
        // Adapter = conecta os dados com o layout
        binding.recyclerGuests.adapter = adapter

        //implementando classe abstrata para clicar no item e fazer alterações
        val listener = object : OnGuestListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)

                //tipo bundle serve para enviar varias informações. Mas neste caso aqui, como seria apenas o ID, poderia enviar apenas  intent.putExtra(chave, id)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)

                //Enviando props para inicializar uma activity
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }

        }
        //passa o listener para o adapter
        adapter.attachListener(listener)

        observe()

        return binding.root
    }

    //Toda vez que entrar em cena, irá atualizar a lista
    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}