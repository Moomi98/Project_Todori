package org.techtown.project_todori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.todori_todori_view.*

class TodoriFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.todori_todori_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        todoriView_change.setOnClickListener {
            todoriView_sampleAnimation.setAnimation("happy.json")
            todoriView_sampleAnimation.playAnimation()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}