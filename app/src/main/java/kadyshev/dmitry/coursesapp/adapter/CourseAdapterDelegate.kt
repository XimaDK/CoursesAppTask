package kadyshev.dmitry.coursesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kadyshev.dmitry.coursesapp.R
import kadyshev.dmitry.coursesapp.databinding.ItemCourseBinding
import kadyshev.dmitry.domain.entities.Course

class CourseAdapterDelegate(
    private val onBookmarkClick: (Course) -> Unit
) : AdapterDelegate<List<Course>>() {

    override fun isForViewType(items: List<Course>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Course>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val course = items[position]
        (holder as CourseViewHolder).bind(course)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            // Привязываем данные к вьюшкам.
            binding.courseTitle.text = course.title
            binding.courseDescription.text = course.text
            binding.date.text = course.startDate
            binding.rating.text = course.rate
            binding.price.text = course.price

            updateBookmarkState(course.hasLike)

            binding.ivBookMark.setOnClickListener {
                val newLiked = !course.hasLike
                onBookmarkClick(course.copy(hasLike = newLiked))
                updateBookmarkState(newLiked)
            }
        }

        private fun updateBookmarkState(isLiked: Boolean) {
            val drawableRes = if (isLiked) {
                R.drawable.ic_bookmark_fill
            } else {
                R.drawable.ic_bookmark
            }
            binding.ivBookMark.setImageResource(drawableRes)
        }
    }
}