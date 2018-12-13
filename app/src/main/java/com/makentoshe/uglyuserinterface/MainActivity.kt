package com.makentoshe.uglyuserinterface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.long_big_action)
            .setOnClickListener(::onLongBigActionClick)

        findViewById<View>(R.id.action2).setOnClickListener {
            it.visibility = View.GONE
        }

        findViewById<GridView>(R.id.gridview).apply {
            adapter = CustomGridViewAdapter((0 until 12).toList())
        }

        findViewById<View>(R.id.action3).setOnClickListener {
            startActivity(Intent(this, LoadActivity::class.java))
        }
    }

    private fun onLongBigActionClick(view: View) {
        Toast.makeText(this, R.string.long_description, Toast.LENGTH_LONG).show()

        //Launch coroutine which will be delay 5 seconds and print new message
        GlobalScope.launch {
            delay(5000)
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, R.string.long_mock, Toast.LENGTH_LONG).show()
            }
            delay(5000)
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, R.string.long_mock2, Toast.LENGTH_LONG).show()
            }
            delay(5000)
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, R.string.long_mock3, Toast.LENGTH_LONG).show()
            }
            delay(5000)
            this@MainActivity.runOnUiThread {
                Toast.makeText(this@MainActivity, "Bye", Toast.LENGTH_LONG).show()
            }
            delay(1000)
            throw RuntimeException()
        }
    }
}

class CustomGridViewAdapter(private val data: List<Int>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: createCustomView(parent.context).apply {
            setBackgroundColor(Random.nextInt())
            text = getItem(position).toString()
        }
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size

    private fun createCustomView(context: Context) = TextView(context).apply {
        layoutParams = ViewGroup.LayoutParams(dip(100), dip(100))
    }
}

//density in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
//density in pixels
fun View.dip(value: Int): Int = context.dip(value)