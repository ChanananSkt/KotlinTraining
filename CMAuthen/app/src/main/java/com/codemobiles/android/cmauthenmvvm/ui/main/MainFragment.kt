package com.codemobiles.android.cmauthenmvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentMainBinding
import com.codemobiles.android.cmauthenmvvm.model.User
import com.codemobiles.android.cmauthenmvvm.viewmodel.AppViewModelFactory
import com.codemobiles.android.cmauthenmvvm.viewmodel.MainViewModel
import com.codemobiles.android.cmauthenmvvm.viewmodel.NavigationCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        val factory = AppViewModelFactory(requireActivity().application)
        mainViewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]
        setEventListener()
        listenNavigationChanged()
        return binding.root
    }

    private fun listenNavigationChanged() {
        mainViewModel.navigationCommand.observe(viewLifecycleOwner) { command ->
            when (command) {
                // Home Page
                is NavigationCommand.HomeDestination -> {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToHomeFragment())
                    mainViewModel.navigationCommand.value = NavigationCommand.NoDestination
                }

                // Success Page
                is NavigationCommand.SuccessDestination -> {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToSuccessFragment())
                    mainViewModel.navigationCommand.value = NavigationCommand.NoDestination
                }

                // Alert Dialog
                is NavigationCommand.AlertDialogDestination ->{
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToAlertDialogFragment(command.title, command.content))
                    mainViewModel.navigationCommand.value = NavigationCommand.NoDestination
                }

                else -> {}
            }
        }

    }

    private fun setEventListener() {
        // Login
        binding.loginBtn.setOnClickListener {
            val username = binding.mUsernameEditText.text.toString()
            val password = binding.mPasswordEditText.text.toString()
            val user = User(username = username, password = password)
            lifecycleScope.launch {   mainViewModel.login(user) }
        }

        // Register
        binding.registerBtn.setOnClickListener {
            val username = binding.mUsernameEditText.text.toString()
            val password = binding.mPasswordEditText.text.toString()
            val user = User(username = username, password = password)

            lifecycleScope.launch { mainViewModel.register(user) }

        }

        // Forgot
        binding.forgotBtn.setOnClickListener {
            mainViewModel.count.value = (mainViewModel.count.value ?: 0) + 1
            binding.forgotBtn.text = "Forgot (${mainViewModel.count.value!!})"
        }
    }

}