package com.pagme.app.ui.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pagme.app.databinding.FragmentAddCardBinding
import com.pagme.app.util.CARD_KEY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCardFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!


    private val viewModel: AddCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        setListeners()
    }

    private fun observeVMEvents() {

        viewModel.cardCreated.observe(viewLifecycleOwner) { card ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(CARD_KEY, card)
                popBackStack()
            }
        }
    }


    private fun setListeners() {

        binding.dialogButtonSaveNewCardView.setOnClickListener {
            val cardName = binding.dialogNameNewCardView.text.toString()
            val closingDate = binding.dialogClosingNewCardView.text.toString()
            val dueDate = binding.dialogDueDateNewCardView.text.toString()

            viewModel.createCard(cardName,closingDate,dueDate)
        }

    }

}