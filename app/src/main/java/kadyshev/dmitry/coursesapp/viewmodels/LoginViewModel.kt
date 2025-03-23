package kadyshev.dmitry.coursesapp.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kadyshev.dmitry.coursesapp.states.LoginFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _formState = MutableStateFlow<LoginFormState>(LoginFormState.Initial)
    val formState = _formState.asStateFlow()

    fun checkFormValidity(email: String, password: String) {

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotBlank()

        _formState.value = when {
            !isEmailValid -> LoginFormState.Invalid
            !isPasswordValid -> LoginFormState.Invalid
            else -> LoginFormState.Valid
        }
    }
}
