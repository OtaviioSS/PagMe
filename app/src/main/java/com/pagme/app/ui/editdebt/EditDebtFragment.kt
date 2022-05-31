package com.pagme.app.ui.editdebt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pagme.app.R

class EditDebtFragment : Fragment() {

    companion object {
        fun newInstance() = EditDebtFragment()
    }

    private lateinit var viewModel: EditDebtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_debt, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditDebtViewModel::class.java)
        // TODO: Use the ViewModel
    }

}