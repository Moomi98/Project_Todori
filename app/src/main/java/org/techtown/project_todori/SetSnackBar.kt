package org.techtown.project_todori

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.snackbar_delete.*
import kotlinx.android.synthetic.main.snackbar_select.*
import kotlinx.android.synthetic.main.snackbar_select.view.*
import kotlinx.android.synthetic.main.todori_calendar_view.*


class SetSnackBar {
    lateinit var snackBar: Snackbar

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMainActivitySnackBar(
        inflater: LayoutInflater,
        activity: Activity,
        holder: TodoListRecyclerViewAdapter.ViewHolder,
        position: Int,
        todoOfDayList: RealmList<Todo>?
    ) { // 스낵바 커스텀 (수정 및 삭제)
        holder.view.setOnLongClickListener { // 길게 누를 시 수정과 삭제 중 선택

            snackBar = Snackbar.make(
                activity.activity_main_view,
                "",
                Snackbar.LENGTH_LONG
            ) // 각 todo에 대한 수정 snackbar 생성

            val setCustomSnackbar = inflater.inflate(R.layout.snackbar_select, null, false)
            val getsnackbarView = snackBar.view as Snackbar.SnackbarLayout

            getsnackbarView.setPadding(0, 0, 0, 0)
            getsnackbarView.addView(setCustomSnackbar)
            getsnackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    R.color.transparent
                )
            )
            snackBar.show()

            setCustomSnackbar.snackbar_modify.setOnClickListener { // 수정을 눌렀을 때
                snackBar.dismiss()
                val intent = Intent(activity, AddTodoActivity::class.java) // 수정 창으로 이동
                intent.putExtra("year", todoOfDayList?.get(position)?.year)
                intent.putExtra("month", todoOfDayList?.get(position)?.month)
                intent.putExtra("day", todoOfDayList?.get(position)?.day)
                intent.putExtra("color", todoOfDayList?.get(position)?.color)
                intent.putExtra("tag", todoOfDayList?.get(position)?.tag)
                intent.putExtra("todo", todoOfDayList?.get(position)?.todo)
                intent.putExtra(
                    "startTime",
                    todoOfDayList?.get(position)?.startTime
                )
                intent.putExtra("endTime", todoOfDayList?.get(position)?.endTime)
                intent.putExtra("requestCode", 2)
                intent.putExtra("requestPosition", position)
                activity.startActivityForResult(intent, 2) // 수정 요청
            }

            setCustomSnackbar.snackbar_delete.setOnClickListener { // 삭제를 눌렀을 때
                snackBar.dismiss()
                val deleteSnackbar = Snackbar.make(
                    activity.activity_main_view,
                    "",
                    Snackbar.LENGTH_INDEFINITE
                )
                val setCustomDeleteSnackbar = inflater.inflate(
                    R.layout.snackbar_delete,
                    null,
                    false
                )
                val getsnackbarDeleteView = deleteSnackbar.view as Snackbar.SnackbarLayout
                getsnackbarDeleteView.setPadding(0, 0, 0, 0)
                getsnackbarDeleteView.addView(setCustomDeleteSnackbar)
                getsnackbarDeleteView.setBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.transparent
                    )
                )

                deleteSnackbar.show()
                val cancel =
                    setCustomDeleteSnackbar.findViewById<TextView>(R.id.snackbar_cancel)
                val reallyDelete =
                    setCustomDeleteSnackbar.findViewById<TextView>(R.id.snackbar_ReallyDelete)
                cancel.setOnClickListener { //  취소 버튼을 눌렀을 때
                    deleteSnackbar.dismiss()
                }

                reallyDelete.setOnClickListener { // 일정을 진짜로 삭제할 때
                    val year = todoOfDayList?.get(position)?.year
                    val month = todoOfDayList?.get(position)?.month
                    val day = todoOfDayList?.get(position)?.day
                    Log.d("del", "$year / $month / $day")
                    AddTodo.deleteTodo(year, month, day, position) // 일정 삭제 트랜잭션 실행
                    Toast.makeText(activity, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    setMainActivityResume(year!!, month!!, day!!, activity) // activity 화면 새로고칩
                    deleteSnackbar.dismiss() // 스낵바 사라짐

                }

            }
            return@setOnLongClickListener true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCalendarSnackBar(
        inflater: LayoutInflater,
        activity: Activity,
        holder: CalendarRecyclerViewAdapter.ViewHolder,
        position: Int,
        todoOfDayList: RealmList<Todo>?
    ) { // 스낵바 커스텀 (수정 및 삭제)
        holder.view.setOnLongClickListener { // 길게 누를 시 수정과 삭제 중 선택

            snackBar = Snackbar.make(
                activity.calendar_main_view,
                "",
                Snackbar.LENGTH_LONG
            ) // 각 todo에 대한 수정 snackbar 생성
            val setCustomSnackbar = inflater.inflate(R.layout.snackbar_select, null, false)
            val getsnackbarView = snackBar.view as Snackbar.SnackbarLayout

            getsnackbarView.setPadding(0, 0, 0, 0)
            getsnackbarView.addView(setCustomSnackbar)
            getsnackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    R.color.transparent
                )
            )
            snackBar.show()

            setCustomSnackbar.snackbar_modify.setOnClickListener { // 수정을 눌렀을 때
                snackBar.dismiss()
                val intent = Intent(activity, AddTodoActivity::class.java) // 수정 창으로 이동
                intent.putExtra("year", todoOfDayList?.get(position)?.year)
                intent.putExtra("month", todoOfDayList?.get(position)?.month)
                intent.putExtra("day", todoOfDayList?.get(position)?.day)
                intent.putExtra("color", todoOfDayList?.get(position)?.color)
                intent.putExtra("tag", todoOfDayList?.get(position)?.tag)
                intent.putExtra("todo", todoOfDayList?.get(position)?.todo)
                intent.putExtra(
                    "startTime",
                    todoOfDayList?.get(position)?.startTime
                )
                intent.putExtra("endTime", todoOfDayList?.get(position)?.endTime)
                intent.putExtra("requestCode", 2)
                intent.putExtra("requestPosition", position)
                activity.startActivityForResult(intent, 2) // 수정 요청
            }

            setCustomSnackbar.snackbar_delete.setOnClickListener { // 삭제를 눌렀을 때
                snackBar.dismiss()
                val deleteSnackbar = Snackbar.make(
                    activity.calendar_main_view,
                    "",
                    Snackbar.LENGTH_INDEFINITE
                )
                val setCustomDeleteSnackbar = inflater.inflate(
                    R.layout.snackbar_delete,
                    null,
                    false
                )
                val getsnackbarDeleteView = deleteSnackbar.view as Snackbar.SnackbarLayout
                getsnackbarDeleteView.setPadding(0, 0, 0, 0)
                getsnackbarDeleteView.addView(setCustomDeleteSnackbar)
                getsnackbarDeleteView.setBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.transparent
                    )
                )

                deleteSnackbar.show()
                val cancel =
                    setCustomDeleteSnackbar.findViewById<TextView>(R.id.snackbar_cancel)
                val reallyDelete =
                    setCustomDeleteSnackbar.findViewById<TextView>(R.id.snackbar_ReallyDelete)
                cancel.setOnClickListener { //  취소 버튼을 눌렀을 때
                    deleteSnackbar.dismiss()
                }

                reallyDelete.setOnClickListener {
                    val year = todoOfDayList?.get(position)?.year
                    val month = todoOfDayList?.get(position)?.month
                    val day = todoOfDayList?.get(position)?.day
                    AddTodo.deleteTodo(year, month, day, position) // 일정 삭제 트랜잭션 실행
                    Toast.makeText(activity, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    setCalendarResume(year!!, month!!, day!!, activity)
                    deleteSnackbar.dismiss()
                }

            }
            return@setOnLongClickListener true
        }
    }


    private fun setMainActivityResume(year: Int, month: Int, day: Int, activity: Activity) {
        val list =
            RealmClass.todoRealm.where(TodoList::class.java) // db에서 오늘 날짜와 같은 리스트에 들어있는 todo찾기
                .equalTo("date", "${year}/${month}/${day}").findAll()
        val adapter = TodoListRecyclerViewAdapter(
            list[0].todoList,
            LayoutInflater.from(activity),
            activity
        )
        activity.main_ViewPager.adapter =
            PageAdapter(activity, activity as FragmentActivity)
    }

    private fun setCalendarResume(year: Int, month: Int, day: Int, activity: Activity) {
        val list =
            RealmClass.todoRealm.where(TodoList::class.java) // db에서 오늘 날짜와 같은 리스트에 들어있는 todo찾기
                .equalTo("date", "${year}/${month}/${day}").findAll()
        val adapter = CalendarRecyclerViewAdapter(
            list[0].todoList,
            LayoutInflater.from(activity),
            activity
        )
        activity.Calendar_recyclerView.adapter = adapter
        activity.Calendar_recyclerView.layoutManager = LinearLayoutManager(activity)
    }

}



