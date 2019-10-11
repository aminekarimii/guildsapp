import com.sqli.guildes.core.ViewModelFactory
import com.sqli.guildes.ui.login.LoginViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ViewModelFactoryTest {

    @Before
    fun setUp() {

    }

    @Test
    fun given_login_view_model_class_to_view_model_factory() {
        try {
            //given
            val viewModelFactory = ViewModelFactory.getInstance(RuntimeEnvironment.application)
            //when
            val result = viewModelFactory.create(LoginViewModel::class.java)
            //then
            assert(result.javaClass.equals(LoginViewModel::class.java))
        }
        catch (e: IllegalArgumentException) {
            assert(false)
        }
    }
}
