package kadyshev.dmitry.coursesapp.states

sealed class LoginFormState {
    data object Initial: LoginFormState()
    data object Valid : LoginFormState()
    data object Invalid : LoginFormState()
}