package org.techtown.project_todori

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.todori_calendar_view.*

class Calendar_Activity : AppCompatActivity() {
    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0
    private var list: RealmResults<TodoList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todori_calendar_view)


        val calIntent = intent
        selectedYear = calIntent.getIntExtra("year", 0)
        selectedMonth = calIntent.getIntExtra("month", 0)
        selectedDay = calIntent.getIntExtra("day", 0)

        list = findtodoList()

        calendar_add.setOnClickListener { // todo 추가 리스너
            val intent = Intent(this, AddTodoActivity::class.java)
            intent.putExtra("year", selectedYear)
            intent.putExtra("month", selectedMonth)
            intent.putExtra("day", selectedDay)
            startActivityForResult(intent, 1)
        }

        Calendar_View.setOnDateChangeListener { view, year, month, dayOfMonth -> // 날짜 선택 시 리스너

            selectedYear = year
            selectedMonth = month + 1
            selectedDay = dayOfMonth

            callRecyclerView()
        }
    }

    override fun onBackPressed() { // 뒤로가기 버튼 누르면 액티비티 종료
        finish()
    }

    override fun onResume() {
        callRecyclerView()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { // 추가된 정보를 저장
        if (resultCode == RESULT_OK) {
            val year = data?.getIntExtra("year", 0)
            val month = data?.getIntExtra("month", 0)
            val day = data?.getIntExtra("day", 0)
            val color = data?.getIntExtra("color", 0)
            val tag = data?.getStringExtra("tag")
            val todo = data?.getStringExtra("todo")
            val startTime = data?.getStringExtra("startTime")
            val endTime = data?.getStringExtra("endTime")

            if (requestCode == 1) { // 오늘 todo 추가 후
                AddTodo.addTodo(year, month, day, color, tag, todo, startTime, endTime)

                RealmClass.todoRealm.executeTransaction {
                    val dataa = it.where(Todo::class.java).findAll()
                    Log.d("realm", dataa.toString())
                }

            } else if (requestCode == 2) {
                val position = data?.getIntExtra("requestPosition", 0)
                AddTodo.modiftyTodo(year, month, day, color, tag, todo, startTime, endTime, position!!)

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun callRecyclerView() { // recyclerView 호출
        list = findtodoList()
        val adapter = CalendarRecyclerViewAdapter(
            list?.get(0)?.todoList,
            LayoutInflater.from(this),
            this
        )
        Calendar_recyclerView.adapter = adapter
        Calendar_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun findtodoList(): RealmResults<TodoList>? {
        val list = RealmClass.todoRealm.where(TodoList::class.java) // db에서 오늘 날짜와 같은 리스트에 들어있는 todo찾기
            .equalTo("date", "${selectedYear}/${selectedMonth}/${selectedDay}").findAll()
        return if(list?.size == 0){
            null
        } else
            list
    }


}

class CalendarRecyclerViewAdapter(
    private val todoOfDayList: RealmList<Todo>?,
    private val inflater: LayoutInflater,
    private val activity: Activity
) : // todoListRecyclerView 의 생성을 돕는 adapter
    RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val todoText: TextView = itemView.findViewById(R.id.todo_view_TodoText)
        val todoTime: TextView = itemView.findViewById(R.id.todo_view_time)
        var todoView: LinearLayout = itemView.findViewById(R.id.todo_view)
    }


    override fun getItemCount(): Int { // 순서 3
        if (todoOfDayList != null) {
            return todoOfDayList.size
        }
        else
            return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 순서 2, 화면에 보여질 곳의 내용들 정하기
        holder.todoText.text = todoOfDayList?.get(position)?.todo
        val startTime = todoOfDayList?.get(position)?.startTime
        val endTime = todoOfDayList?.get(position)?.endTime
        holder.todoTime.text = "$startTime - $endTime"
        val a = ContextCompat.getDrawable(
            activity,
            R.drawable.sample_rounded_layout
        ) as GradientDrawable
        todoOfDayList?.get(position)?.color?.let { a.setColor(it) }
        holder.todoView.background = a

        SetSnackBar().setCalendarSnackBar(
            inflater,
            activity,
            holder,
            position,
            todoOfDayList
        ) // 스낵바 커스텀 설정

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 순서 1
        val view = inflater.inflate(R.layout.todori_todo_view, parent, false)
        return ViewHolder(view)
    }

}