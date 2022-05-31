package com.pagme.app.ui.registeruser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pagme.app.R
import com.pagme.app.databinding.FragmentRegisterUserBinding
import com.pagme.app.util.CARD_KEY
import com.pagme.app.util.USER_KEY

class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        setListeners()
    }

    private fun observeVMEvents() {

        viewModel.userCreated.observe(viewLifecycleOwner) { user ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                popBackStack()
            }
        }
    }

    private fun setListeners()  {

        binding.buttonSaveNewUser.setOnClickListener {
            val email = binding.emailCreateUserView.text.toString()
            val password = binding.passwordCreateUserView.text.toString()
            val name = binding.userNameCreateUserView.text.toString()

            viewModel.createUser(email, password, name)
        }


    }


}