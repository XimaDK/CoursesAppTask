package kadyshev.dmitry.coursesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kadyshev.dmitry.coursesapp.R
import kadyshev.dmitry.coursesapp.databinding.FragmentOnboardingBinding
import kadyshev.dmitry.coursesapp.states.OnboardingState
import kadyshev.dmitry.coursesapp.viewmodels.OnboardingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding: FragmentOnboardingBinding
        get() = _binding ?: throw RuntimeException("FragmentOnboardingBinding == null")

    private lateinit var navController: NavController

    private val viewModel: OnboardingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        binding.btnContinue.setOnClickListener {
            viewModel.onContinueClicked()
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is OnboardingState.NavigateToLogin -> {
                            navController.navigate(R.id.action_onboardingFragment_to_loginFragment)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}