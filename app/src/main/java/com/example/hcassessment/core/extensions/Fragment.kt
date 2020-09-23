package com.example.hcassessment.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this, factory).get(T::class.java) //ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}
