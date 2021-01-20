package org.techtown.project_todori

import android.util.Log
import android.widget.Toast
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults

open class AddTodo{
    companion object{
        fun addTodo(year : Int?, month : Int?, day : Int?, color : Int?, tag: String?, todo: String?, startTime: String?, endTime: String?){
            var query : RealmResults<TodoList>? = RealmClass.todoRealm.where(TodoList::class.java).equalTo("date", "${year}/${month}/${day}").findAll()

            if(query?.size == 0){
                query = null
            }
            Log.d("query", query.toString())
            val makeTodo = Todo()
            makeTodo.year = year
            makeTodo.month = month
            makeTodo.day = day
            makeTodo.color = color
            makeTodo.tag = tag
            makeTodo.todo = todo
            makeTodo.startTime = startTime
            makeTodo.endTime = endTime

            if (query == null){
                RealmClass.todoRealm.executeTransaction {
                    with(it.createObject(TodoList::class.java)){
                        this.date = "${year}/${month}/${day}"
                        this.todoList.add(makeTodo)
                        Log.d("query", this.date)
                    }
                }
            }

            else{
                RealmClass.todoRealm.executeTransaction {

                        query[0].todoList.add(makeTodo)

                    Log.d("query", query[0].todoList.toString())

                }
            }
        }

        fun modiftyTodo(year : Int?, month : Int?, day : Int?, color : Int?, tag: String?, todo: String?, startTime: String?, endTime: String?, position : Int){
            val query = RealmClass.todoRealm.where(TodoList::class.java).equalTo("date", "${year}/${month}/${day}").findAll()
            if(query != null){
                RealmClass.todoRealm.executeTransaction {
                    Log.d("pos", query.toString())
                    query[0].todoList[position].year = year
                    query[0].todoList[position].month = month
                    query[0].todoList[position].day = day
                    query[0].todoList[position].color = color
                    query[0].todoList[position].tag = tag
                    query[0].todoList[position].todo = todo
                    query[0].todoList[position].startTime = startTime
                    query[0].todoList[position].endTime = endTime
                }
            }
        }

        fun deleteTodo(year : Int?, month : Int?, day : Int?, position : Int){
            val query = RealmClass.todoRealm.where(TodoList::class.java).equalTo("date", "${year}/${month}/${day}").findAll()
            Log.d("pos", query.toString())
            if(query != null){
                RealmClass.todoRealm.executeTransaction {
                    query[0].todoList.removeAt(position)
                }
            }
        }
    }
}

open class TodoList : RealmObject(){
    var date : String = ""
    var todoList : RealmList<Todo> = RealmList()
}

open class Todo : RealmObject(){
    var year : Int? = 0
    var month : Int? = 0
    var day : Int? = 0
    var color : Int? = -952971 // default 컬러 값
    var tag: String? = null
    var todo: String? = null
    var startTime: String? = null
    var endTime: String? = null
} // 날짜와 할 일 목록을 저장
