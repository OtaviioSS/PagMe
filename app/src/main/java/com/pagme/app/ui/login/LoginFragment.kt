package com.pagme.app.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.pagme.app.R
import com.pagme.app.databinding.FragmentCardListBinding
import com.pagme.app.databinding.FragmentLoginBinding
import com.pagme.app.domain.model.Card
import com.pagme.app.ui.card.CardListViewModel
import com.pagme.app.util.CARD_KEY
import com.pagme.app.util.DEBT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        setListeners()
    }


    private fun setListeners() {

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailLoginView.text.toString()
            val password = binding.passwordLoginView.text.toString()

            viewModel.uerLogin(email, password)
        }

    }


    private fun observeVMEvents() {
        viewModel.userLogged.observe(viewLifecycleOwner) { user ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(DEBT_KEY, user)
                popBackStack()
            }
        }
    }


}