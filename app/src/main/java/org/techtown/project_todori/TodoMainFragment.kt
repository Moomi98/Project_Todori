package org.techtown.project_todori

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.main_ViewPager
import kotlinx.android.synthetic.main.todori_todolist_view.*
import java.util.*

class TodoMainFragment(private val activity: Activity) : Fragment() {
    private var mBackWait: Long = 0 // 뒤로가기 대기 시간
    private var showYear: Int = 0
    private var showMonth: Int = 0
    private var showDay: Int = 0
    private var list: RealmResults<TodoList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cal = Calendar.getInstance() // 오늘 날짜 설정
        showYear = cal.get(Calendar.YEAR)
        showMonth = cal.get(Calendar.MONTH) + 1
        showDay = cal.get(Calendar.DATE)
        Log.d("date", showYear.toString())

        list = findtodoList()
        Log.d("here", list.toString())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.todori_todolist_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TodoListRecyclerViewAdapter(list?.get(0)?.todoList, LayoutInflater.from(activity), activity)
        todoList_recyclerView.adapter = adapter
        todoList_recyclerView.layoutManager = LinearLayoutManager(activity)


        TopBar_Calendar.setOnClickListener { // 캘린더 호출 리스너
                    val intent = Intent(activity, Calendar_Activity::class.java)
                    intent.putExtra("year", showYear)
                    intent.putExtra("month", showMonth)
                    intent.putExtra("day", showDay)
                    activity.startActivityForResult(intent, 0)
                }
                TopBar_Add.setOnClickListener { // todo 추가 리스너
                    val intent = Intent(activity, AddTodoActivity::class.java)
                    intent.putExtra("year", showYear)
                    intent.putExtra("month", showMonth)
                    intent.putExtra("day", showDay)
                    activity.startActivityForResult(intent, 1)
                }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        list = findtodoList()
        val adapter = TodoListRecyclerViewAdapter(list?.get(0)?.todoList, LayoutInflater.from(activity), activity)
        todoList_recyclerView.adapter = adapter
        todoList_recyclerView.layoutManager = LinearLayoutManager(activity)

        super.onResume()
    }

    private fun findtodoList(): RealmResults<TodoList>? {
        val list = RealmClass.todoRealm.where(TodoList::class.java) // db에서 오늘 날짜와 같은 리스트에 들어있는 todo찾기
            .equalTo("date", "${showYear}/${showMonth}/${showDay}").findAll()
        return if(list?.size == 0){
            null
        } else
            list
    }
}

class TodoListRecyclerViewAdapter(
    private val todoOfDayList: RealmList<Todo>?,
    private val inflater: LayoutInflater,
    private val activity: Activity
) : // todoListRecyclerView 의 생성을 돕는 adapter
    RecyclerView.Adapter<TodoListRecyclerViewAdapter.ViewHolder>() {

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

        SetSnackBar().setMainActivitySnackBar(
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
