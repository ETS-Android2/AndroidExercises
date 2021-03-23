package com.example.historialproductos

/**
 * Based on code from: https://abhiandroid.com/ui/searchview
 */

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.provider.BaseColumns
import android.text.TextUtils
import android.view.*
import androidx.appcompat.widget.*
import android.widget.*
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import org.jetbrains.anko.doAsync
import android.util.Log
import androidx.annotation.MainThread
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import androidx.core.view.size
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.jetbrains.anko.uiThread
import kotlin.concurrent.thread


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var productDB: RoomSingleton

    lateinit var adapter: ListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDB = RoomSingleton.getInstance(requireContext())
        var productNames = mutableListOf<String>()
        doAsync {

            var listProduct = productDB.productDao().getAll()
            var productBrand = mutableListOf<String>()
            for (p in listProduct) {
                productBrand.add(
                    p.name.toString() + ", " + productDB.brandDao().getNameByKey(p.keyBrand)
                )// Product, Brand
            }

            for (p in productBrand) {
                productNames.add(p)
            }

        }.get()
        // Locate the ListView in fragment_search
        var list_search = view.findViewById<ListView>(R.id.listView_search)

        // Pass results to ListViewAdapter Class
        adapter = ListViewAdapter(this.requireContext(), productNames)
        Log.d("ProductNames->", "Size = " + productNames.size)

        // Binds the Adapter to the ListView
        list_search.setAdapter(adapter)

        list_search.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

        var searchViewText = view.findViewById<SearchView>(R.id.searchViewText)

        list_search.setOnItemClickListener { parent, view1, position, id ->
            var elementPos = adapter.getItemId(position).toInt()
            var elementTxt = list_search.getItemAtPosition(elementPos) as String
            searchViewText.setQuery(elementTxt, false)
            //Toast.makeText(this.context, "You clicked " + elementTxt, Toast.LENGTH_SHORT).show()

            // strip product - brand
            var arrayPB = stripProductBrand(elementTxt) //Text from the searchViewText
            var listProductCountability: List<PriceDate>

            var textViewCountability =
                view.findViewById<TextView>(R.id.textView_countability) //Set reference to textView in layout

            var textViewData = mutableListOf<String>()

            // Function for the text
            //setTextViewAccountability(arrayPB, textViewData, textViewCountability)

            view.findViewById<TableLayout>(R.id.tableAccountability)?.visibility =
                View.VISIBLE // Hide table layout
            doAsync {
                var keyP = productDB.productDao().getKeyByNameBrand(
                    arrayPB[0],
                    productDB.brandDao().getKeyByName(arrayPB[1])
                ) // Set key product
                var product = productDB.productDao().getProductByKey(keyP)
                //textViewCountability.text = "Hello"
                textViewData.add("Unit: " + product.unit.toString() + "\n") //Add text of amount of the product
                //textViewCountability.append("Weight: " + product.weight.toString() + "\n") //Add weight of the product
                textViewData.add("Weight: " + product.weight.toString() + "\n") //Add weight of the product

                listProductCountability = productDB.priceDateDao()
                    .getAllByProduct(keyP) // Get all the stuff you have bought

                var mutableListAccountability = mutableListOf<Array<String>>()
                for (PriceDate in listProductCountability) { // text to show the accountability of a product
                    var arr = Array(5) { "" }
                    arr.set(0, PriceDate.date.toString()) // First column --> Date
                    arr.set(
                        1,
                        productDB.storeDao().getNameByKey(PriceDate.keyStore).toString()
                    ) // Second column --> Store
                    arr.set(2, PriceDate.price.toString()) // Third column --> Price
                    arr.set(3, PriceDate.priceReduced.toString()) // Fourth column --> Price reduced
                    arr.set(4, PriceDate.discount.toString()) // Fifth column --> Discount
                    mutableListAccountability.add(arr)
                }
                uiThread {
                    //Thread.sleep(1500) // Wait for a good load
                    for (t in textViewData) {
                        view.findViewById<TextView>(R.id.textView_countability).append(t)
                    }
                    fillTableAccountability(mutableListAccountability) // Create and fill the table
                }
            }
        }

        // Locate the EditText in listview_main
        var editSearch = view.findViewById<SearchView>(R.id.searchViewText)
        editSearch.setOnQueryTextListener(this)

        view.findViewById<Button>(R.id.button_return).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentSearch_to_FirstFragment)
        }
    }

    @Override
    override fun onQueryTextSubmit(query: String): Boolean {

        return false
    }

    @Override
    override fun onQueryTextChange(newText: String): Boolean {
        if (TextUtils.isEmpty(newText)) { // check if search bar is empty
            // clean accountability
            view?.findViewById<TextView>(R.id.textView_countability)?.text = ""
            var tableA = view?.findViewById<TableLayout>(R.id.tableAccountability)
            tableA?.visibility = View.GONE // Hide table layout
            //tableA?.removeAllViews()
            while (tableA?.size!! > 1) { // Remove all rows except header
                tableA.removeViewAt(1)
            }
        }
        var text = newText
        adapter.filter(text)
        return false
    }

    private fun stripProductBrand(string: String): Array<String> {
        // ideally indexOfFirst, but I did not manage to make it work
        var index = 0 // first position
        var c = ','
        for (s in string) {
            if (s.compareTo(c) == 0) {
                break
            }
            index++
        }

        var product = string.substring(0, index) //product,
        index += 2 // ", " add two positions
        var brand = string.substring(index, string.length)//, brand
        return arrayOf(product, brand)
    }

    private fun fillTableAccountability(mutableList: MutableList<Array<String>>) {
        // Table set data accountability
        var tableA = view?.findViewById<TableLayout>(R.id.tableAccountability) // Set link to XML
        for (m in mutableList) {
            // Create header row
            var rowN = TableRow(this.requireContext()) // For each row
            var textR1 = TextView(this.requireContext()) // For each textView
            var textR2 = TextView(this.requireContext()) // For each textView
            var textR3 = TextView(this.requireContext()) // For each textView
            var textR4 = TextView(this.requireContext()) // For each textView
            var textR5 = TextView(this.requireContext()) // For each textView
            //textR1.layoutParams(TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT))
            textR1.setText(m[0])
            textR1.setTextColor(Color.GRAY)
            textR1.gravity = Gravity.CENTER
            textR1.setBackgroundResource(R.drawable.text_rectangle)
            textR1.setPadding(4, 0, 4, 0)
            textR1.setPaddingRelative(4, 0, 4, 0)
            rowN.addView(textR1)
            textR2.setText(m[1])
            textR2.setTextColor(Color.GRAY)
            textR2.gravity = Gravity.CENTER
            textR2.setBackgroundResource(R.drawable.text_rectangle)
            rowN.addView(textR2)
            textR3.setText(m[2])
            textR3.setTextColor(Color.GRAY)
            textR3.gravity = Gravity.CENTER
            textR3.setBackgroundResource(R.drawable.text_rectangle)
            rowN.addView(textR3)
            textR4.setText(m[3])
            textR4.setTextColor(Color.GRAY)
            textR4.gravity = Gravity.CENTER
            textR4.setBackgroundResource(R.drawable.text_rectangle)
            rowN.addView(textR4)
            textR5.setText((m[4]))
            textR5.setTextColor(Color.GRAY)
            textR5.gravity = Gravity.CENTER
            textR5.setBackgroundResource(R.drawable.text_rectangle)
            rowN.addView(textR5)
            tableA?.addView(rowN)
            // Data header rows
        }

    }

    private fun setTextViewAccountability(
        arrayPB: Array<String>,
        textViewData: MutableList<String>,
        textViewCountability: TextView
    ) {
        // TextView set data accountability
        doAsync {
            var listProductCountability: List<PriceDate>
            var keyP = productDB.productDao().getKeyByNameBrand(
                arrayPB[0],
                productDB.brandDao().getKeyByName(arrayPB[1])
            ) // Set key product
            var product = productDB.productDao().getProductByKey(keyP)
            //textViewCountability.text = "Hello"
            textViewData.add("Unit: " + product.unit.toString() + "\n") //Add text of amount of the product
            //textViewCountability.append("Weight: " + product.weight.toString() + "\n") //Add weight of the product
            textViewData.add("Weight: " + product.weight.toString() + "\n") //Add weight of the product
            listProductCountability = productDB.priceDateDao()
                .getAllByProduct(keyP) // Get all the stuff you have bought

            for (PriceDate in listProductCountability) { // text to show the accountability of a product
                textViewData.add("----------\n") //add("----------\n") //Rough separation --->UGLY!
                textViewData.add("Date: " + PriceDate.date + "\n") //Add date of the buy
                textViewData.add(
                    "Store: " + productDB.storeDao().getNameByKey(PriceDate.keyStore) + "\n"
                ) //Add store
                textViewData.add("Price: " + PriceDate.price) //Add the price of the product that day
                if (PriceDate.priceReduced != 0.0) {
                    textViewData.add(" -> " + PriceDate.priceReduced + "\n")
                    textViewData.add("Discount: " + PriceDate.discount)
                }
                textViewData.add("\n")//set the next line
            }

            uiThread {
                Log.d("TextViewData-->", textViewData.size.toString())
                for (d in textViewData) {
                    textViewCountability.append(d)
                }
            }
        }
    }
}