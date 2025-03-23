package kadyshev.dmitry.coursesapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kadyshev.dmitry.domain.entities.Course

class DiffUtilCourseAdapter(
    onBookmarkClick: (Course) -> Unit
) : ListDelegationAdapter<List<Course>>() {

    init {
        delegatesManager.addDelegate(CourseAdapterDelegate(onBookmarkClick))
        items = emptyList()
    }

    fun submitList(newCourses: List<Course>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = newCourses.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == newCourses[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newCourses[newItemPosition]
            }
        })
        items = newCourses
        diffResult.dispatchUpdatesTo(this)
    }
}
