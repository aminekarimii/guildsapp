import android.util.Log
import com.sqli.guildes.BuildConfig
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun Disposable.disposeWith(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)

fun Any.log(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.simpleName, message)
    }
}

val Any?.safe get() = Unit