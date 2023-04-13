package com.example.convidados.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.AllGuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllGuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle? ): View {
        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //RECYCLERVIEW - LAYOUT
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        //RECYCLERVIEW - ADAPTER
        // Adapter = conecta os dados com o layout
        binding.recyclerAllGuests.adapter = adapter

        //implementando classe abstrata para clicar no item e fazer alterações
        val listener = object : OnGuestListener{
            override fun onClick(id: Int) {
                Toast.makeText(context, "Fui clicado" + id, Toast.LENGTH_SHORT).show()
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }

        }
        //passa o listener para o adapter
        adapter.attachListener(listener)

        viewModel.getAll()

        observe()

        return binding.root
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