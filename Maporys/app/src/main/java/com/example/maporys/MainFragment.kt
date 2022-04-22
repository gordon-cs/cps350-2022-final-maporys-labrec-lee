import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


class MainFragment : Fragment(), View.OnClickListener {
    private var fragment_btn_1: Button? = null
    private var fragment_btn_2: Button? = null
    @Nullable
    fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        fragment_btn_1 = view.findViewById(R.id.fragment_button_1) as Button
        fragment_btn_2 = view.findViewById(R.id.fragment_button_2) as Button
        fragment_btn_1.setOnClickListener(this)
        fragment_btn_2.setOnClickListener(this)
        return view
    }

    fun onClick(v: View) {
        when (v.getId()) {
            R.id.fragment_button_1 -> {
                val fragment1: Fragment = Fragment1()
                moveToFragment(fragment1)
            }
            R.id.fragment_button_2 -> {
                val fragment2: Fragment = Fragment2()
                moveToFragment(fragment2)
            }
        }
    }

    private fun moveToFragment(fragment: Fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName())
            .addToBackStack(null).commit()
    }
}