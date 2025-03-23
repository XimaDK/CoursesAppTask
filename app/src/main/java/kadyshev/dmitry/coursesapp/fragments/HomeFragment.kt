package kadyshev.dmitry.coursesapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kadyshev.dmitry.coursesapp.adapter.DiffUtilCourseAdapter
import kadyshev.dmitry.coursesapp.databinding.FragmentHomeBinding
import kadyshev.dmitry.coursesapp.states.HomeFragmentState
import kadyshev.dmitry.coursesapp.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSortButton()
        observeView()
    }

    private fun observeView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    Log.d("STATE", state.toString())
                    when (state) {
                        is HomeFragmentState.Loading -> showLoading()
                        is HomeFragmentState.Success -> showCourses(state)
                        is HomeFragmentState.Error -> showError(state)
                    }
                }
            }
        }
    }

    private fun showError(state: HomeFragmentState.Error) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showCourses(state: HomeFragmentState.Success) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
        (binding.recyclerView.adapter as? DiffUtilCourseAdapter)?.submitList(
            state.courses
        )
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun setupSortButton() {
        binding.btnSortByDate.setOnClickListener {
            viewModel.sortCoursesDescending()
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setupRecyclerView() {
        val adapter = DiffUtilCourseAdapter(
            onBookmarkClick = { course -> viewModel.onBookmarkClick(course) },
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}