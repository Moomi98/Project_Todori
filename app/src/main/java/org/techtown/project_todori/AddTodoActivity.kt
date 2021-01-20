package org.techtown.project_todori

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.todori_addtodo_view.*
import kotlinx.android.synthetic.main.todori_calendar_view.*
import kotlinx.android.synthetic.main.todori_select_time_view.*
import kotlinx.android.synthetic.main.todori_todo_view.*

class AddTodoActivity : AppCompatActivity() {
    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0
    private var requestCode: Int = 0
    private var requestPosition: Int = 0
    private var color: Int = 0
    private var tag: String? = ""
    private var todo: String? = ""
    private var startTime: String? = ""
    private var endTime: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todori_addtodo_view)

        val Intent = intent

        selectedYear = Intent.getIntExtra("year", 0)
        selectedMonth = Intent.getIntExtra("month", 0)
        selectedDay = Intent.getIntExtra("day", 0)
        requestCode = Intent.getIntExtra("requestCode", 0)

        // 밑 부분은 수정요청을 하였을 때 필요한 추가 정보
        if (requestCode == 2) {
            requestPosition = Intent.getIntExtra("requestPosition", 0)
            color = Intent.getIntExtra("color", 0)
            tag = Intent.getStringExtra("tag")
            todo = Intent.getStringExtra("todo")
            startTime = Intent.getStringExtra("startTime")
            endTime = Intent.getStringExtra("endTime")

        }
        checkModifty(requestCode) // 수정 요청인지 확인


        selectColor() // 색상 지정
        selectTag() // 태그 지정
        selectTime() // 시간 설정

        todo_whatToDo.setOnKeyListener { v, keyCode, event ->
            keyCode == KEYCODE_ENTER
        }
        addButton.setOnClickListener { // 추가, 수정 버튼

            startTime = todo_startTime.text.toString()
            endTime = todo_endTime.text.toString()
            todo = todo_whatToDo.text.toString()

            val intent = Intent()

            intent.putExtra("year", selectedYear)
            intent.putExtra("month", selectedMonth)
            intent.putExtra("day", selectedDay)
            intent.putExtra("tag", tag)
            intent.putExtra("todo", todo)
            intent.putExtra("startTime", startTime)
            intent.putExtra("endTime", endTime)
            intent.putExtra("color", color)
            intent.putExtra("requestPosition", requestPosition)
            setResult(RESULT_OK, intent)

            if(requestCode == 1)
                Toast.makeText(this, "일정이 추가되었습니다", Toast.LENGTH_SHORT).show()
            else if(requestCode == 2)
                Toast.makeText(this, "일정이 수정되었습니다", Toast.LENGTH_SHORT).show()

            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { // 시간 설정 완료 후
        if (resultCode == RESULT_OK) {
            var changeHour: String = ""
            var changeMin: String = ""
            val hour = data?.getIntExtra("hour", 0)
            val min = data?.getIntExtra("min", 0)

            if (hour != null) {
                changeHour = if (hour < 10) {
                    "0$hour"
                } else
                    hour.toString()
            }

            if (min != null) {
                changeMin = if (min < 10) {
                    "0$min"
                } else
                    min.toString()
            }

            when (requestCode) {
                0 -> todo_startTime.text = "$changeHour : $changeMin" // 시작 시간 보여주기
                1 -> todo_endTime.text = "$changeHour : $changeMin" // 종료 시간 보여주기
            }
        } else {
            Log.d("time", "error")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun selectColor() {  // layoutBox 의 색상 지정
        selected_color_green.setOnClickListener {
            todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_green)
            color = ContextCompat.getColor(this, R.color.green)
        }
        selected_color_red.setOnClickListener {
            todo_layoutBox.background = ContextCompat.getDrawable(this, R.drawable.select_color_red)
            color = ContextCompat.getColor(this, R.color.red)
        }
        selected_color_pink.setOnClickListener {
            todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_pink)
            color = ContextCompat.getColor(this, R.color.pink)
        }
        selected_color_purple.setOnClickListener {
            todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_purple)
            color = ContextCompat.getColor(this, R.color.purple)
        }
        selected_color_skyblue.setOnClickListener {
            todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_skyblue)
            color = ContextCompat.getColor(this, R.color.skyblue)
        }
        selected_color_yellow.setOnClickListener {
            todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_yellow)
            color = ContextCompat.getColor(this, R.color.yellow)
        }
        selected_color_init.setOnClickListener {
            todo_layoutBox.background = ContextCompat.getDrawable(this, R.drawable.rounded_layout)
            color = ContextCompat.getColor(this, R.color.init)

        }
        selected_color_orange.setOnClickListener {
            todo_layoutBox.background = ContextCompat.getDrawable(
                this,
                R.drawable.select_color_orange
            )
           color = ContextCompat.getColor(this, R.color.orange)
        }
    }

    private fun selectTag() { // 태그 지정
        val tagButtonList = ArrayList<ImageButton>()
        tagButtonList.add(tagButton_routine)
        tagButtonList.add(tagButton_sleep)
        tagButtonList.add(tagButton_eat)
        tagButtonList.add(tagButton_selfImprovement)
        tagButtonList.add(tagButton_freeTime)

        tagButton_eat.setOnClickListener {
            checkButtonState(tagButton_eat, tagButtonList)
            tag = tagButton_eatText.text.toString()
        }

        tagButton_freeTime.setOnClickListener {
            checkButtonState(tagButton_freeTime, tagButtonList)
            tag = tagButton_freeTimeText.text.toString()
        }

        tagButton_routine.setOnClickListener {
            checkButtonState(tagButton_routine, tagButtonList)
            tag = tagButton_routineText.text.toString()
        }

        tagButton_selfImprovement.setOnClickListener {
            checkButtonState(tagButton_selfImprovement, tagButtonList)
            tag = tagButton_selfImprovementText.text.toString()
        }

        tagButton_sleep.setOnClickListener {

            checkButtonState(tagButton_sleep, tagButtonList)
            tag = tagButton_sleepText.text.toString()
        }
    }

    private fun selectTime() { // 시간 설정
        val intent = Intent(this, SelectTime::class.java)
        todo_startTime.setOnClickListener { // 시작시간 버튼 활성화
            startActivityForResult(intent, 0)
        }

        todo_endTime.setOnClickListener { // 종료시간 버튼 활성화
            startActivityForResult(intent, 1)
        }

    }

    private fun checkButtonState(button: ImageButton, buttonList: ArrayList<ImageButton>) {
        for (i in buttonList) {
            if (i == button)
                button.isSelected = true
            else
                i.isSelected = false
        }
    }

    private fun checkModifty(requestCode: Int) {
        if (requestCode == 2) { // 수정 요청을 하였다면
            setTodoLayoutBox(color)
            setTag(tag)
            todo_whatToDo.text.replace(0, todo_whatToDo.text.length, todo)
            todo_startTime.text = startTime
            todo_endTime.text = endTime
            addTodo_menuText.text = "수정"
            addButton.text = "수정"
        }
    }

    private fun setTodoLayoutBox(color: Int) { // 수정시 레이아웃 박스 컬러 설정
        when (color) {
            ContextCompat.getColor(this, R.color.green) -> {
                todo_layoutBox.background =
                    ContextCompat.getDrawable(this, R.drawable.select_color_green)
            }
            ContextCompat.getColor(this, R.color.red)->{todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_green)}
            ContextCompat.getColor(this, R.color.pink) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_pink)}
            ContextCompat.getColor(this, R.color.purple) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_purple)}
            ContextCompat.getColor(this, R.color.skyblue) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_skyblue)}
            ContextCompat.getColor(this, R.color.yellow) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_yellow)}
            ContextCompat.getColor(this, R.color.init) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.rounded_layout)}
            ContextCompat.getColor(this, R.color.orange) -> {todo_layoutBox.background =
                ContextCompat.getDrawable(this, R.drawable.select_color_orange)}
        }

    }

    private fun setTag(tag : String?){ // 수정시 태그 설정
        when(tag){
            tagButton_routineText.text ->{
                tagButton_routine.isSelected = true
            }
            tagButton_sleepText.text ->{
                tagButton_sleep.isSelected = true
            }
            tagButton_eatText.text ->{
                tagButton_eat.isSelected = true
            }
            tagButton_selfImprovementText.text ->{
                tagButton_selfImprovement.isSelected = true
            }
            tagButton_freeTimeText.text ->{
                tagButton_freeTime.isSelected = true
            }
        }
    }
}