import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.launch

fun <T: Any> ViewModel.toObservable(task: suspend () -> T) = Observable.create<T> { emitter ->
    viewModelScope.launch {
        try {
            emitter.onNext(task())
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }
}
