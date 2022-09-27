package com.junior.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.junior.todo.adapter.TarefaAdapter
import com.junior.todo.databinding.FragmentListBinding
import com.junior.todo.model.Tarefa

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)


        val listTarefas = listOf(
            Tarefa(
                "Lavar a louça",
                "Lavar a louça do dia todo",
                "Junior",
                "2022-09-27",
                true,
                "Dia a dia"

            ),
            Tarefa(
                "Lavar a louça",
                "Lavar a louça do dia todo",
                "Junior",
                "2022-09-27",
                true,
                "Dia a dia"

            ),
            Tarefa(
                "Lavar a louça",
                "Lavar a louça do dia todo",
                "Junior",
                "2022-09-27",
                true,
                "Dia a dia"

            )
        )

        //Configuração do recycleView
        val adapter = TarefaAdapter()
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)

        adapter.setList(listTarefas)

        // ----------------- //
        binding.floatingAdd.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }
        return binding.root
    }
}