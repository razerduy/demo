package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.webservice.WebService


class MainActivity : AppCompatActivity() {

    var listItem: ArrayList<String> = ArrayList()

    lateinit var listKeyword: RecyclerView
    lateinit var btTryAgain: Button
    var listKeywordAdapter: AdapterKeyword? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListKeyword()
        setUpLayoutButton()
        fetchData()
    }

    private fun setUpLayoutButton() {
        btTryAgain = findViewById(R.id.btTry)
        btTryAgain.setOnClickListener {
            fetchData()
        }
    }

    private fun setUpListKeyword() {
        listKeywordAdapter = AdapterKeyword(listItem)
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        listKeyword = findViewById(R.id.listKeyword)
        listKeyword.layoutManager = layoutManager
        listKeyword.adapter = listKeywordAdapter
    }

    private fun refreshListKeyword() {
        listKeywordAdapter?.notifyDataSetChanged()
    }

    private fun fetchData() {
        val webServiceInterface = WebService.getWebServiceInterface()
        val callCancel = webServiceInterface!!.keyWords
        val getKeywordDelegate = GetKeywordDelegate(this)
        callCancel.enqueue(getKeywordDelegate)
    }

    private class GetKeywordDelegate constructor(activity: MainActivity): Callback<ArrayList<String>> {
        val TAG: String = GetKeywordDelegate::class.java.simpleName
        private val activityWeekReference: WeakReference<MainActivity>

        init {
            this.activityWeekReference = WeakReference<MainActivity>(activity)
        }


        override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
            val activity = activityWeekReference.get()
           activity?.let {
               val checkAppIsNotDestroyed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                   !activity.isFinishing && !activity.isDestroyed
               } else {
                   !activity.isFinishing
               }
               if (checkAppIsNotDestroyed) {
                   activity.listItem.clear()
                   activity.listItem.addAll(response.body())
                   activity.refreshListKeyword()
               }
           }
        }

        override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
            activityWeekReference.get()?.let {
                t.message?.let {
                    Toast.makeText(activityWeekReference.get()!!.applicationContext, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
