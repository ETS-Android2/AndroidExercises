package com.example.historialproductos

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text
import java.util.*
import kotlin.coroutines.coroutineContext

class ListViewAdapter(var context: Context, var namesList: MutableList<String>) : BaseAdapter() {
    var inflater: LayoutInflater
    var arrayList: MutableList<String>

    init {
        inflater = LayoutInflater.from(context)
        arrayList = mutableListOf()
        arrayList.addAll(namesList)
        //Log.d("ListViewAdapter->",arrayList.get(0))
    }

    class ViewHolder(context: Context) {
        var name: TextView

        init {
            name = TextView(context)
        }
        //lateinit var name: TextView
    }

    @Override
    override fun getCount(): Int {
        return namesList.size
    }

    @Override
    override fun getItem(position: Int): String {
        return namesList.get(position)
    }

    @Override
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @Override
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var holder: ViewHolder
        var viewT: View
        if (view == null) {
            //Log.d("ListViewAdapter->","View is null")
            //print ("Error in view = null")
            holder = ViewHolder(context)
            //var viewT: View
            //viewT = inflater.inflate(R.layout.listview_item, null)
            viewT = inflater.inflate(R.layout.listview_item, null)

            // Locate the TextViews in listView_item.xml
            holder.name = viewT.findViewById<TextView>(R.id.name)
            viewT.setTag(holder)
            // Set the results into TextViews
            holder.name.setText(namesList.get(position))
            holder.name.setTextColor(Color.parseColor("#000000"))

            return viewT
        } else {
            if (view.tag != null) {
                //Log.d("InsideTag->","Check me please")
                holder = view.tag as ViewHolder
                // Set the results into TextViews
                holder.name.setText(namesList.get(position))
                holder.name.setTextColor(Color.parseColor("#000000"))
                holder.name.layoutParams
                // Change width
                holder.name.layoutParams.width = MATCH_PARENT

                return view
            } else {
                //Log.d("ListViewAdapter->","The tag is null")
                holder = ViewHolder(context)
                //var viewT: View
                viewT = inflater.inflate(R.layout.listview_item, null)
                // Locate the TextViews in listView_item.xml
                holder.name = viewT.findViewById<TextView>(R.id.name)
                viewT.setTag(holder)
                // Set the results into TextViews
                holder.name.setText(namesList.get(position))
                holder.name.setTextColor(Color.parseColor("#000000"))

                // Change width
                holder.name.layoutParams.width = MATCH_PARENT
                return viewT
            }
            //return view
        }

    }

    //Filter class
    fun filter(charText: String) {
        var charTextT: String
        charTextT = charText.toLowerCase(Locale.getDefault())
        namesList.clear()
        if (charTextT.length == 0) {
            //Log.d("ListViewAdapter->","Filter length is 0")
            namesList.addAll(arrayList)
        } else {
            //Log.d("ListViewAdapter->","Filter length is" + charText.length)
            for (n in arrayList) {
                if (n.toLowerCase(Locale.getDefault()).contains(charText)) {
                    namesList.add(n)
                }
            }
        }
        notifyDataSetChanged()
    }

}