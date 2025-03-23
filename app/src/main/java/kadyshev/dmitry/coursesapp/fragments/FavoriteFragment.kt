package kadyshev.dmitry.coursesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kadyshev.dmitry.coursesapp.adapter.DiffUtilCourseAdapter
import kadyshev.dmitry.coursesapp.databinding.FragmentFavoriteBinding
import kadyshev.dmitry.coursesapp.states.FavoriteFragmentState
import kadyshev.dmitry.coursesapp.states.HomeFragmentState
import kadyshev.dmitry.coursesapp.viewmodels.FavoriteViewModel
import kadyshev.dmitry.domain.entities.Course
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoriteBinding == null")

    private val viewModel: FavoriteViewModel by viewModel()

    private lateinit var adapter: DiffUtilCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = DiffUtilCourseAdapter { course ->
            viewModel.onBookmarkClick(course)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is FavoriteFragmentState.Loading -> showLoading()
                        is FavoriteFragmentState.Success -> showCourses(state.courses)
                        is FavoriteFragmentState.Error -> showError(state.message)
                    }
                }
            }

        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.tvContainerText.visibility = View.GONE
    }

    private fun showCourses(courses: List<Course>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = if (courses.isEmpty()) View.GONE else View.VISIBLE
        binding.tvContainerText.visibility = if (courses.isEmpty()) View.VISIBLE else View.GONE
        binding.tvContainerText.text = "Список избранных курсов пуст"
        adapter.submitList(courses)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}