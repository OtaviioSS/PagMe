package com.pagme.app.ui.debt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.pagme.app.R
import com.pagme.app.databinding.FragmentDebtListBinding
import com.pagme.app.domain.model.Debt
import com.pagme.app.ui.card.CardListFragment
import com.pagme.app.util.DEBT_KEY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DebtListFragment : Fragment() {

    private var _binding: FragmentDebtListBinding? = null
    private val binding get() = _binding!!
    private lateinit var toggle: ActionBarDrawerToggle

    private val viewModel: DebtListViewModel by viewModels()


    private val debtAdapter = DebtAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDebtListBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        toggle = ActionBarDrawerToggle(activity, binding.drawerDebts, R.string.open, R.string.close)
        binding.drawerDebts.addDrawerListener(toggle)
        toggle.syncState()
        setListeners()
        observeNavBackStack()
        observeVMEvents()
        getDebts()
    }


    private fun setRecyclerView() {
        binding.recyclerDebits.run {
            setHasFixedSize(true)
            adapter = debtAdapter
        }


    }


    private fun setListeners() {
        with(binding) {
            swipeDebts.setOnRefreshListener {
                getDebts()
            }
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_debtListFragment_to_addDebtFragment)

            }

            fab.setBackgroundResource(R.drawable.fab_background)

            navigationViewMainView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.myCardsMenuDrawaerMain ->
                        findNavController().navigate(R.id.action_debtListFragment_to_cardListFragment2)
                    R.id.myAccountMenuDrawaerMain ->
                        findNavController().navigate(R.id.action_debtListFragment_to_profileFragment)


                }
                true
            }


        }
    }

    private fun observeNavBackStack() {
        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.debtListFragment)
            val savedStateHandle = navBackStackEntry.savedStateHandle
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(DEBT_KEY)) {
                    val debt = savedStateHandle.get<Debt>(DEBT_KEY)
                    val oldList = debtAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        add(debt)
                    }
                    debtAdapter.submitList(newList)
                    binding.recyclerDebits.smoothScrollToPosition(newList.size - 1)
                    savedStateHandle.remove<Debt>(DEBT_KEY)
                }
            }

            navBackStackEntry.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    private fun observeVMEvents() {
        viewModel.debtsData.observe(viewLifecycleOwner) { debts ->
            binding.swipeDebts.isRefreshing = false
            debtAdapter.submitList(debts)
        }


    }

    /*   private fun verifyAuthentication() {
           val currentUser = auth.currentUser
           if (currentUser == null) {
               val intent = Intent(this, Activity_Login::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(intent)
               finish()
           }
       }



       override fun onOptionsItemSelected(item: MenuItem): Boolean {
           if (toggle.onOptionsItemSelected(item)) {
               return true
           }

           return super.onOptionsItemSelected(item)
       }*/

    private fun getDebts() {
        viewModel.getDebts()
    }


}


