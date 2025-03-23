package kadyshev.dmitry.coursesapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kadyshev.dmitry.coursesapp.databinding.ActivityMainBinding
import kadyshev.dmitry.coursesapp.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding == null")


    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        enableEdgeToEdge()
        setupBottomNav()
        setupNav()
        setupObservers()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isOnboardingCompleted.collectLatest { isCompleted ->
                    val navController =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            ?.findNavController()
                    if (isCompleted) {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.onboardingFragment, true)
                            .build()
                        navController?.navigate(R.id.homeFragment, null, navOptions)
                    } else {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.onboardingFragment, true)
                            .build()
                        navController?.navigate(R.id.onboardingFragment, null, navOptions)
                    }
                }
            }
        }
    }

    private fun setupNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onboardingFragment, R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}