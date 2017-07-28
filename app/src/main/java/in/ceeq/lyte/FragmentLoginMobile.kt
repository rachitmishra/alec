package `in`.ceeq.lyte

import `in`.ceeq.lyte.databinding.FragmentLoginMobileBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class FragmentLoginMobile : Fragment() {

    companion object {
        fun getInstance(args: Bundle = Bundle()) : FragmentLoginMobile {
            return FragmentLoginMobile().arguments = args
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState:Bundle?): View? {
        val binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_login_mobile, container)
        val view = binding.root
        return view
    }
}
