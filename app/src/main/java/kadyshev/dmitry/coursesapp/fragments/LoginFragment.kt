package kadyshev.dmitry.coursesapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kadyshev.dmitry.coursesapp.R
import kadyshev.dmitry.coursesapp.databinding.FragmentLoginBinding
import kadyshev.dmitry.coursesapp.states.LoginFormState
import kadyshev.dmitry.coursesapp.viewmodels.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupTextListeners()
        setupButtons()
    }

    private fun setupButtons() = with(binding) {
        iconVk.setOnClickListener {
            openUrl("https://vk.com/")
        }
        iconOk.setOnClickListener {
            openUrl("https://ok.ru/")
        }
        btnLogin.setOnClickListener {
            navigateToMainScreen()
        }
    }

    private fun setupTextListeners() = with(binding) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.checkFormValidity(etEmail.text.toString(), etPassword.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.formState.collect { state ->
                    when (state) {
                        is LoginFormState.Invalid -> binding.btnLogin.isEnabled = false
                        is LoginFormState.Valid -> binding.btnLogin.isEnabled = true
                        is LoginFormState.Initial -> {}

                    }
                }
            }
        }
    }


    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
