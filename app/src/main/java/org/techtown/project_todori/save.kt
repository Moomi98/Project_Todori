 package org.techtown.project_todori
//
//class save {
//
//    override fun getCount(): Int { // tabview에 표시 될 pager의 개수 리턴
//        return 4
//    }
//
//    override fun isViewFromObject(
//        view: View,
//        `object`: Any
//    ): Boolean { // 현재 view가 안드로이드가 보는 view와 같은지 확인
//        return view === `object` as View
//    }
//
//
//    override fun instantiateItem(
//        container: ViewGroup,
//        position: Int
//    ): Any { // tabview의 위치에 따라 보여질 뷰 설정
//        when (position) {
//            0 -> { // 메인  todo 화면
//                val view = layoutInflater.inflate(R.layout.todori_todolist_view, container, false)
//                container.addView(view)
//                activity.todoList_recyclerView.adapter = adapter
//                activity.todoList_recyclerView.layoutManager = LinearLayoutManager(activity)
//
//                val calendar = activity.findViewById<ImageButton>(R.id.TopBar_Calendar)
//                calendar.setOnClickListener { // 캘린더 호출 리스너
//                    val intent = Intent(activity, Calendar_Activity::class.java)
//                    intent.putExtra("year", year)
//                    intent.putExtra("month", month)
//                    intent.putExtra("day", day)
//                    activity.startActivityForResult(intent, 0)
//                }
//                activity.TopBar_Add.setOnClickListener { // todo 추가 리스너
//                    val intent = Intent(activity, AddTodoActivity::class.java)
//                    intent.putExtra("year", year)
//                    intent.putExtra("month", month)
//                    intent.putExtra("day", day)
//                    activity.startActivityForResult(intent, 1)
//                }
//
//
//                return view
//            }
//
//            1 -> { // 투두리 화면면
//                val view = layoutInflater.inflate(R.layout.todori_todori_view, container, false)
//                container.addView(view)
//                return view
//            }
//
//            2 -> {
//                val view =
//                    layoutInflater.inflate(R.layout.todori_setting_view, container, false)
//                container.addView(view)
//                return view
//            }
//
//            else -> {
//                val view = layoutInflater.inflate(R.layout.todori_setting_view, container, false)
//                container.addView(view)
//                return view
//            }
//
//        }
//    }
//
//
//
//    override fun destroyItem(
//        container: ViewGroup,
//        position: Int,
//        `object`: Any
//    ) { // 다른 뷰로 이동시 이전 뷰 삭제
//        container.removeView(`object` as View)
//    }

// main_ViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(main_tabView))
//}