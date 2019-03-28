import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
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

@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}