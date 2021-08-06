package com.emikhalets.myfinances.ui.transactions

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.myfinances.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private val binding by viewBinding(FragmentTransactionsBinding::bind)
    private val homeViewModel by viewModels<TransactionsVM>()
}