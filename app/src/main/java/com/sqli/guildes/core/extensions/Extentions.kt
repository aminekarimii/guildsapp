import android.util.Log
import com.sqli.guildes.BuildConfig


fun Any.log(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.simpleName, message)
    }
}

val Any?.safe get() = Unit

