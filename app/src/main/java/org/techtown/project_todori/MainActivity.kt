package org.techtown.project_todori

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todori_todolist_view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mBackWait: Long = 0 // 뒤로가기 대기 시간
    private var showYear: Int = 0
    private var showMonth: Int = 0
    private var showDay: Int = 0
    private var list: RealmResults<TodoList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RealmClass.initRealm(this) // realm DB 생성

//        val cal = Calendar.getInstance() // 오늘 날짜 설정
//        showYear = cal.get(Calendar.YEAR)
//        showMonth = cal.get(Calendar.MONTH) + 1
//        showDay = cal.get(Calendar.DATE)
//        Log.d("date", showYear.toString())
//
        // list = findtodoList()
//        Log.d("here", list.toString())
//
//        val adapter = TodoListRecyclerViewAdapter(list?.get(0)?.todoList, LayoutInflater.from(this), this)
        main_ViewPager.adapter = PageAdapter(this, this)


//        main_tabView.addTab(main_tabView.newTab().setIcon(R.drawable.menu_todolist))
//        main_tabView.addTab(main_tabView.newTab().setIcon(R.drawable.menu_todori_sample))
//        main_tabView.addTab(main_tabView.newTab().setIcon(R.drawable.menu_setting))
//
//
//        main_tabView.addOnTabSelectedListener(object :
//            TabLayout.OnTabSelectedListener { // 탭과 화면이 같이 전환
//            override fun onTabSelected(tab: TabLayout.Tab?) { // 탭이 선택되었을 때
//                main_ViewPager.currentItem = tab!!.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) { // 탭이 선택되지 않았을 때
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) { // 탭을 다시 선택할 떄
//            }
//
//        })

        val tabIconList = arrayListOf(
            R.drawable.menu_todolist,
            R.drawable.menu_todori_sample,
            R.drawable.menu_setting,
        )
        TabLayoutMediator(main_tabView, main_ViewPager) { tab, position ->
            tab.setIcon(tabIconList[position])

        }.attach()


    }

    override fun onBackPressed() {

        if (System.currentTimeMillis() - mBackWait >= 1000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish() //액티비티 종료
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                AddTodo.modiftyTodo(
                    year,
                    month,
                    day,
                    color,
                    tag,
                    todo,
                    startTime,
                    endTime,
                    position!!
                )

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


//    override fun onResume() {
//        list = findtodoList()
//        val adapter = TodoListRecyclerViewAdapter(list?.get(0)?.todoList, LayoutInflater.from(this), this)
//        main_ViewPager.adapter =
//            PageAdapter(this)
//        super.onResume()
//    }

//    private fun findtodoList(): RealmResults<TodoList>? {
//        val list = RealmClass.todoRealm.where(TodoList::class.java) // db에서 오늘 날짜와 같은 리스트에 들어있는 todo찾기
//            .equalTo("date", "${showYear}/${showMonth}/${showDay}").findAll()
//        return if(list?.size == 0){
//            null
//        } else
//            list
//    }

}

//class TodoListRecyclerViewAdapter(
//    private val todoOfDayList: RealmList<Todo>?,
//    private val inflater: LayoutInflater,
//    private val activity: Activity
//) : // todoListRecyclerView 의 생성을 돕는 adapter
//    RecyclerView.Adapter<TodoListRecyclerViewAdapter.ViewHolder>() {
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val view = itemView
//        val todoText: TextView = itemView.findViewById(R.id.todo_view_TodoText)
//        val todoTime: TextView = itemView.findViewById(R.id.todo_view_time)
//        var todoView: LinearLayout = itemView.findViewById(R.id.todo_view)
//
//    }
//
//
//    override fun getItemCount(): Int { // 순서 3
//        if (todoOfDayList != null) {
//            return todoOfDayList.size
//        }
//        else
//            return 0
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 순서 2, 화면에 보여질 곳의 내용들 정하기
//        holder.todoText.text = todoOfDayList?.get(position)?.todo
//        val startTime = todoOfDayList?.get(position)?.startTime
//        val endTime = todoOfDayList?.get(position)?.endTime
//        holder.todoTime.text = "$startTime - $endTime"
//        val a = ContextCompat.getDrawable(
//            activity,
//            R.drawable.sample_rounded_layout
//        ) as GradientDrawable
//        todoOfDayList?.get(position)?.color?.let { a.setColor(it) }
//        holder.todoView.background = a
//
//        SetSnackBar().setMainActivitySnackBar(
//            inflater,
//            activity,
//            holder,
//            position,
//            todoOfDayList
//        ) // 스낵바 커스텀 설정
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 순서 1
//        val view = inflater.inflate(R.layout.todori_todo_view, parent, false)
//
//        return ViewHolder(view)
//    }
//
//}


class PageAdapter(
    private val activity: Activity,
    fragment: FragmentActivity,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
         return when (position) {
            0 -> {
                 TodoMainFragment(activity)
            }
            1 -> TodoriFragment()
            2 -> SettingFragment()
            else -> TodoMainFragment(activity)
        }
    }

}
