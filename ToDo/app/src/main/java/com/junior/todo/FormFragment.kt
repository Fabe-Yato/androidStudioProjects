package com.junior.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.junior.todo.databinding.FragmentFormBinding
import com.junior.todo.fragment.DatePickerFragment
import com.junior.todo.fragment.TimerPickerListener
import com.junior.todo.model.Categoria
import com.junior.todo.model.Tarefa
import java.time.LocalDate


class FormFragment : Fragment(), TimerPickerListener {

    private lateinit var binding: FragmentFormBinding

    // indicando que toda instancias que utilizar activityViewModels consegue criar uma instancia para sobreviver
    private val mainViewModel: MainViewModel by activityViewModels()
    private var categoriaSelecionada = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)

        mainViewModel.listCategoria()
        mainViewModel.dataSelecionada.value = LocalDate.now()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner){
            response -> Log.d("Requisicao", response.body().toString())
            spinnerCategoria(response.body())

        }

        mainViewModel.dataSelecionada.observe(viewLifecycleOwner){
            selectDate -> binding.editData.setText(selectDate.toString())
        }

        binding.buttonSalvar.setOnClickListener{
            inserirNoBanco()
        }

        binding.editData.setOnClickListener {
            DatePickerFragment(this).show(parentFragmentManager, "DatePicker")
        }
        return binding.root
    }



    private fun spinnerCategoria(listCategoria: List<Categoria>?){
        if(listCategoria != null){
            binding.spinnerCategoria.adapter =
                ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    listCategoria
                )

            binding.spinnerCategoria.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selected = binding.spinnerCategoria.selectedItem as Categoria

                        categoriaSelecionada = selected.id
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
        }
    }

    private fun validarCampos(nome: String, descricao: String, responsavel: String): Boolean{
        return !(
                (nome == "" || nome.length < 3 || nome.length > 20) ||
                        (descricao == "" || descricao.length < 5 || descricao.length > 200) ||
                        (responsavel == "" || responsavel.length < 3 || responsavel.length > 20)
                )
    }

    private fun inserirNoBanco(){
        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val resp = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria = Categoria(categoriaSelecionada, null, null)

        if(validarCampos(nome, desc, resp)){
            val tarefa = Tarefa( 0 ,nome, desc, resp, data, status, categoria )
            mainViewModel.addTarefa(tarefa)
            Toast.makeText(context, "Tarefa criada", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }else{
            Toast.makeText(context, "Verique os campos 😢", Toast.LENGTH_LONG).show()
        }
    }
    override fun onDateSelected(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }
}

