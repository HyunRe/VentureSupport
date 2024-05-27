package com.example.venturesupport

//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class SchedulerActivity : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: ScheduleAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.scheduler)
//
//        recyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        fetchSchedules()
//    }
//
//    private fun fetchSchedules() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.0.8:3306/app/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(ApiService::class.java)
//        val call = apiService.getSchedules()
//
//        call.enqueue(object : Callback<List<Schedule>> {
//            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>) {
//                if (response.isSuccessful) {
//                    val schedules = response.body()
//                    if (schedules != null) {
//                        adapter = ScheduleAdapter(schedules)
//                        recyclerView.adapter = adapter
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
//                Toast.makeText(this@SchedulerActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SchedulerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var scheduleList = ArrayList<Schedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scheduler)

        recyclerView = findViewById(R.id.recycler_view)
        scheduleAdapter = ScheduleAdapter(scheduleList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = scheduleAdapter

        // 여기서 스케줄 데이터를 데이터베이스에서 가져와서 scheduleList에 추가하는 코드를 작성합니다.
        // 예를 들어, scheduleList.add(Schedule("제목", "날짜", "시간"))
        scheduleList.add(Schedule("농축수산", "경기 포천시 신북면 중앙로 209 210", "2024.05.24 09:30",  "유한락스 2L", "필요 수량: 12"))
        scheduleList.add(Schedule("에이블 마트", "경기 포천시 내촌면 내촌로 41", "2024.05.24 12:00",  "피죤(R) 핑크 2.1L+2.1L", "필요 수량: 4"))
        scheduleList.add(Schedule("에이블 슈퍼", "서울 성북구 장위로 53", "2024.05.24 13:00",  "옥시크린(R) 700g", "필요 수량: 7"))
        scheduleList.add(Schedule("에이식품", "경기 포천시 중앙로115번길 10", "2024.05.24 14:30",  "부라보(트리오) 14KG", "필요 수량: 5"))
        scheduleList.add(Schedule("홈마트A", "서울 노원구 동일로242길 5 1층 수락홈마트", "2024.05.24 15:00",  "퍼실 일반용기(라벤더) 2.7L", "필요 수량: 4"))

        // RecyclerView에 각 아이템 사이의 간격을 설정하는 ItemDecoration을 추가합니다.
        val itemDecoration = ScheduleItemDecoration(resources.getDimensionPixelSize(R.dimen.item_margin))
        recyclerView.addItemDecoration(itemDecoration)
    }
}