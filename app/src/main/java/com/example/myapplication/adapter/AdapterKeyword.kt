package com.example.myapplication.adapter

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.StringBuilder
import java.util.*
import kotlin.math.abs
import android.graphics.drawable.GradientDrawable
import android.widget.Toast
import com.example.myapplication.R


class AdapterKeyword(val data: List<String>) :
    RecyclerView.Adapter<AdapterKeyword.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_keyword, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TAG: String = RecyclerViewHolder::class.java.simpleName
        var arrayColors = arrayOf(
            "#19443c",
            "#262335",
            "#464647",
            "#442200",
            "#4d4b50",
            "#1d0002",
            "#112222",
            "#777672",
            "#3d4c51",
            "#2a4041",
            "#3c0006",
            "#36576a",
            "#2b3230"
        )

        internal var txtKeyword: TextView = itemView.findViewById(R.id.txtKeyword) as TextView
        internal var llbackground: LinearLayout =
            itemView.findViewById(R.id.background) as LinearLayout

        init {
            val rnd = Random()
            llbackground.setBackgroundResource(R.drawable.background_keyword)
            val drawable = llbackground.background as GradientDrawable
            drawable.setColor(Color.parseColor(arrayColors[rnd.nextInt(arrayColors.size)]))
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, txtKeyword.text, Toast.LENGTH_SHORT).show()
            }
        }

        private fun findContentForFirstLine(parts: List<String>, keyword: String): StringBuilder {
            val lengthMax = keyword.length / 2
            Log.d(TAG, "lengthMax: $lengthMax")
            val textBuilder = StringBuilder().append(parts[0])
            for (i in 1..parts.size - 1) {
                if (textBuilder.length + parts[i].length <= lengthMax) {
                    if (i == 1) {
                        textBuilder.append(" ").append(parts[i])
                    } else {
                        textBuilder.append(" ").append(parts[i])
                    }
                } else {
                    val lengthTextIfContinue =
                        textBuilder.length + parts[i].length + 1 + 1 // cong them part
                    val lengthLastTextIfContinue =
                        abs(keyword.length - (textBuilder.length + parts[i].length + 1 + 1)) // cong them part

                    val lengthTextIfBreak = textBuilder.length + 1 // ngat dong
                    val lengthLastTextIfBreak =
                        abs(keyword.length - (textBuilder.length + 1)) // ngat dong

                    if (abs(lengthTextIfContinue - lengthLastTextIfContinue) <= abs(
                            lengthTextIfBreak - lengthLastTextIfBreak
                        )
                    ) {
                        textBuilder.append(" ").append(parts[i]).append("\n")
                    } else {
                        textBuilder.append("\n")
                    }
                    break
                }
            }
            return textBuilder
        }

        fun setData(keyword: String) {
            val parts = keyword.split(" ")
            Log.d(TAG, "parts: ${parts.size} $parts")
            when (parts.size) {
                1 -> txtKeyword.text = keyword
                else -> {
                    val textBuilder = findContentForFirstLine(parts, keyword)
                    if (textBuilder.length < keyword.length) {
                        Log.d(TAG, "contentLast ${textBuilder.length} " + textBuilder)
                        Log.d(TAG, "contentLast ${keyword.length} " + keyword)
                        val contentLast = keyword.substring(
                            textBuilder.length,
                            keyword.trim().length
                        )
                        Log.d(TAG, "contentLast " + contentLast)
                        textBuilder.append(contentLast)
                    }
                    txtKeyword.text = textBuilder.trim()
                }
            }
        }
    }

}