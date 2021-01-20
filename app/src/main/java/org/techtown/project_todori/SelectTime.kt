package org.techtown.project_todori

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.todori_select_time_view.*

class SelectTime : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.todori_select_time_view)


        select_timeButton.setOnClickListener { // 시간, 분 정보 전달
            val hour = select_time.hour
            val min  = select_time.minute
            val intent = Intent()
            intent.putExtra("hour", hour)
            intent.putExtra("min", min)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}