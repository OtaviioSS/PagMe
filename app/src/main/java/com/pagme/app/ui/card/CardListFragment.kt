package com.pagme.app.ui.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.pagme.app.databinding.FragmentCardListBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.pagme.app.R
import com.pagme.app.domain.model.Card
import com.pagme.app.util.CARD_KEY

@AndroidEntryPoint
class CardListFragment : Fragment() {

    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardListViewModel by viewModels()

    private val cardAdapter = CardAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setListeners()
        observeNavBackStack()
        observeVMEvents()

        getCards()
    }

    private fun setRecyclerView() {
        binding.recyclerCard.run {
            setHasFixedSize(true)
            adapter = cardAdapter
        }
    }

    private fun setListeners() {
        with(binding) {
            swipeCards.setOnRefreshListener {
                getCards()
            }
        }
    }

    private fun getCards() {
        viewModel.getCards()
    }

    private fun observeNavBackStack() {
        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.cardListFragment)
            val savedStateHandle = navBackStackEntry.savedStateHandle
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(CARD_KEY)) {
                    val product = savedStateHandle.get<Card>(CARD_KEY)
                    val oldList = cardAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        add(product)
                    }
                    cardAdapter.submitList(newList)
                    binding.recyclerCard.smoothScrollToPosition(newList.size - 1)
                    savedStateHandle.remove<Card>(CARD_KEY)
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
        viewModel.cardsData.observe(viewLifecycleOwner) { products ->
            binding.swipeCards.isRefreshing = false
            cardAdapter.submitList(products)
        }


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}