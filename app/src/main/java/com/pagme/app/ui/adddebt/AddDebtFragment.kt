package com.pagme.app.ui.adddebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pagme.app.R
import com.pagme.app.databinding.FragmentAddDebtBinding
import com.pagme.app.util.DEBT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDebtFragment : Fragment() {

    private var _binding: FragmentAddDebtBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddDebtViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAddDebtBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        setListeners()
    }

    private fun observeVMEvents() {
        viewModel.debtCreated.observe(viewLifecycleOwner) { debt ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(DEBT_KEY, debt)
                popBackStack()
            }
        }
    }



    private fun setListeners() {

        binding.buttonSaveNewDebtView.setOnClickListener {
            val nameCard =  binding.spinnerCardNewDebtView
            val nameBuyer =  binding.nameBuyerNewDebtView
            val valueBuy = binding.valueBuyNewDebtView
            val installments = binding.installmentsNewDebtView
            val paidInstallments = 0
            val whatsapp = binding.whatsappNewDebtView
            val valueInstallments = binding.valueInstallmentsNewDebtView
            val idCard = binding.spinnerCardNewDebtView

            viewModel.createDebt(nameCard.toString(),nameBuyer.toString(),valueBuy.toString().toDouble(),installments.toString().toInt(),paidInstallments,whatsapp.toString(),valueInstallments.toString().toDouble(),idCard.toString())

        }

        binding.btnOpenViewNewDebtView.setOnClickListener {

            findNavController().navigate(R.id.action_addDebtFragment_to_addCardFragment)

        }

    }






}