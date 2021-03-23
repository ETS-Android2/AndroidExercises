package com.example.historialproductos

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {

    private lateinit var productDB: RoomSingleton
    val REQUEST_CODE = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // default date
        var dummyDay = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())
        // Date Pick
        var textDate = view.findViewById<EditText>(R.id.editTextDate)
        textDate.setText(dummyDay) // Default date
        textDate.setOnClickListener { v ->
            val newFragment = DatePickerFragment()
            newFragment.setTargetFragment(
                this,
                REQUEST_CODE
            ) // Set communication with Dialog Fragment
            newFragment.show(this.parentFragmentManager, "datePicker") // Show Dialog
        }

        // search name
        var autoCompleteTextViewProduct =
            view.findViewById<AutoCompleteTextView>(R.id.autoCompleteInsert)
        // get possible products
        productDB = RoomSingleton.getInstance(requireContext())
        var list: List<String> = emptyList()
        var listType: List<String> = emptyList()
        var listBrand: List<String> = emptyList()
        var listStore: List<String> = emptyList()
        // check if error in list persists => doAsyncResult().get and uiThread from anko
        doAsync {
            list = productDB.productDao().getNameAll() // set the name of all the products
            listType = productDB.typeDao().getNameAll() // set the name of all the types
            listBrand = productDB.brandDao().getNameAll() // get the name of all the brands
            listStore = productDB.storeDao().getNameAll() // get the name of all the stores
        }.get() // Put in wait main thread till the process finish
        val adapterProduct =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, list)
        autoCompleteTextViewProduct.setAdapter(adapterProduct)
        autoCompleteTextViewProduct.threshold = 1 // start searching from 1 character
        autoCompleteTextViewProduct.setAdapter(adapterProduct) // set the adapter for displaying list

        // radio-group for type
        var radioGroupType = view.findViewById<RadioGroup>(R.id.radioGroup_type)
        var ID = 0
        for (s in listType) {
            var rB = RadioButton(this.requireContext())
            rB.id = ID
            rB.text = s
            radioGroupType.addView(rB)
            ID++ // increase the ID
        }

        // search brand
        var autoCompleteTextBrand = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteBrand)
        //doAsync { listBrand = productDB.brandDao().getNameAll() }
        var adapterBrand =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, listBrand)
        autoCompleteTextBrand.setAdapter(adapterBrand)
        autoCompleteTextBrand.threshold = 1
        autoCompleteTextBrand.setAdapter(adapterBrand)

        //search store
        var autoCompleteTextStore = view.findViewById<AutoCompleteTextView>(R.id.autoComplete_store)
        var adapterStore =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, listStore)
        autoCompleteTextStore.setAdapter(adapterStore)
        autoCompleteTextStore.threshold = 1
        autoCompleteTextStore.setAdapter(adapterStore)



        var buttonAddToList = view.findViewById<Button>(R.id.button_addToList)
        buttonAddToList.setOnClickListener {
            Toast.makeText(this.context, R.string.fixing, Toast.LENGTH_SHORT).show()
        }



        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            // Insert data
            doAsync {
                var editTextPriceUser = view.findViewById<EditText>(R.id.editTextNumber_price) // set variable for text of the price
                // get product, brand, store that the user put
                var productUser= autoCompleteUserPut(autoCompleteTextViewProduct) // Set the text of the user
                var brandUser= autoCompleteUserPut(autoCompleteTextBrand) // Set the text of the brand
                var storeUser= autoCompleteUserPut(autoCompleteTextStore) // Set the text of the store
                var priceUser= editTextPriceUser.text // Set the text of the price
                var typeUser = radioGroupType.checkedRadioButtonId // Set the option (number) of the type
                var discountText = view.findViewById<EditText>(R.id.editTextNumber_discount) // Get data of field discount
                var discountUser: Double // Number of the discount
                if(discountText.text.isEmpty()){
                    discountUser = 0.0 // Set null discount
                }else{
                    discountUser = discountText.text.toString().toDouble() // Set the discount of the user
                }
                //Log.d("InsertData-->", "Radio button " + typeUser.toString())
                if(productUser.isEmpty() or brandUser.isEmpty() or storeUser.isEmpty() or priceUser.isEmpty() or (typeUser == -1) or ((discountUser < 0.0) or (discountUser > 100.0))){
                    // Something is incomplete
                    uiThread {
                        var toast = Toast.makeText(context, R.string.errorInsertProduct, Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER,Gravity.CENTER, Gravity.CENTER)
                        toast.show()
                        if(productUser.isEmpty()){
                            autoCompleteTextViewProduct.setError(getString(R.string.errorProduct)) // Set error for product
                        }
                        if(brandUser.isEmpty()){
                            autoCompleteTextBrand.setError(getString(R.string.errorBrand)) // Set error for brand
                        }
                        if(storeUser.isEmpty()){
                            autoCompleteTextStore.setError(getString(R.string.errorStore)) // Set error for store
                        }
                        if(priceUser.isEmpty()){
                            editTextPriceUser.setError(getString(R.string.errorPrice)) // Set error for price
                        }
                        if(typeUser == -1){
                            Toast.makeText(context, R.string.errorType, Toast.LENGTH_SHORT).show() // Set error for type of user
                        }
                        if((discountUser < 0) or (discountUser > 100.0)){
                            discountText.setError(getString(R.string.errorDiscount)) // Set error for the discount
                        }
                    }
                }
                else{
                    var keyStore = productDB.storeDao().getKeyByName(storeUser) // Get key for store
                    var keyBrand = productDB.brandDao().getKeyByName(brandUser) // Get key for brand
                    var radioButton = radioGroupType.findViewById<RadioButton>(typeUser)
                    var textButton = radioButton.text.toString()

                    var keyType = productDB.typeDao().getKeyByName(textButton)  // Get key for type
                    var unityText = view.findViewById<EditText>(R.id.editTextNumber_unity).text
                    var weightText = view.findViewById<EditText>(R.id.editTextNumberDecimal_weight).text
                    var dateText = view.findViewById<EditText>(R.id.editTextDate).text

                    var dateUser: String
                    var unityUser: Int
                    var weightUser: Double
                    var priceReduced = priceUser.toString().toDouble()-((discountUser/100.0)*priceUser.toString().toDouble()) // Set discount if there is

                    if(unityText.isEmpty()) { // Check if unity field is empty
                        unityUser = 1 // Set amount 0
                    }else{
                        unityUser = unityText.toString().toInt() // Set user typed amount
                    }
                    if(weightText.isEmpty()){
                        weightUser = 0.0
                    }else{
                        weightUser = weightText.toString().toDouble() // Set user typed weight
                    }
                    if(dateText.isEmpty()){
                        uiThread {  Toast.makeText(context, "There is an error with your date", Toast.LENGTH_SHORT).show()  }
                        dateUser = ""
                    }else{
                        dateUser = dateText.toString()
                    }

                    if(keyStore == 0){ // Store does not exist
                        productDB.storeDao().insert(Store(0,storeUser)) // Insert new store
                        keyStore = productDB.storeDao().getKeyByName(storeUser) // Get key created
                    }
                    if(keyBrand == 0){ // Brand does not exist
                        productDB.brandDao().insert(Brand(0, brandUser)) // Insert new brand
                        keyBrand = productDB.brandDao().getKeyByName(brandUser) // Get key created
                    }
                    var keyProduct = productDB.productDao().getKeyByName(productUser) // get key of the product if exist
                    //Log.d("InsertData-->", "Name product = " + productUser.toString())
                    if(keyProduct == 0){ // Name product does not exist
                        productDB.productDao().insert(Product(0, keyType, keyBrand, productUser, unityUser, weightUser, "" )) // Insert new product
                        keyProduct = productDB.productDao().getKeyIfProductExist(productUser,keyBrand,keyType)
                    }else{ // Product exists
                        keyProduct = productDB.productDao().getKeyIfProductExist(productUser, keyBrand, keyType)
                        if(keyProduct == 0) { // Product name does not exist with that brand or type
                            productDB.productDao().insert(
                                Product(
                                    0,
                                    keyType,
                                    keyBrand,
                                    productUser,
                                    unityUser,
                                    weightUser,
                                    ""
                                )
                            ) // Insert product
                        }
                        keyProduct = productDB.productDao().getKeyIfProductExist(productUser, keyBrand, keyType) // Get key of the new product
                    }
                    productDB.priceDateDao().insert(PriceDate(0, keyProduct, keyStore, priceUser.toString().toDouble(), discountUser, priceReduced, dateUser)) // Insert price date
                    uiThread {
                        Toast.makeText(context, "New data has been added!", Toast.LENGTH_SHORT)
                        findNavController().navigate(R.id.action_FragmentRegister_to_FirstFragment)
                    }
                }
            }.get() // wait till this thread finish
            // Close fragment and returns to the main menu
            //findNavController().navigate(R.id.action_FragmentRegister_to_FirstFragment)
        }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    if (data.extras != null) {
                        if (data.extras!!.containsKey("datePicker")) {
                            var dateSet = data.extras!!.getString("datePicker")
                            Log.d("OnActivityResult->", dateSet.toString())
                            var textDate = view?.findViewById<EditText>(R.id.editTextDate)
                            textDate?.setText(dateSet.toString())
                        }
                    }
                }
            }
        }
    }

    private fun autoCompleteUserPut(auto: AutoCompleteTextView): String{
        if(auto.onItemSelectedListener != null){
            return auto.onItemSelectedListener.toString()  // The user selected an option
        }
        else{
            return auto.text.toString() // The user inserted her own words
        }
    }

}