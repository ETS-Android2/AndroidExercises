package com.example.historialproductos

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.jetbrains.anko.doAsync
import java.io.*
import java.lang.IllegalStateException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

class AlertDialogFragment : DialogFragment() {

    fun showAlertImport(
        activity: Activity,
        context: Context,
        sourceDatabase: String,
        room: RoomSingleton
    ): AlertDialog {
        return activity.let {
            //return context?.let{
            //var builder = AlertDialog.Builder(it)
            var builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.dialog_import_title)
            builder.setMessage(R.string.dialog_import_database)
                .setPositiveButton(R.string.importD,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User export database
                        var check = false
                        var fileS = File(sourceDatabase)
                        if (fileS.canWrite()) {
                            Log.d("Directory-->", "Can Write")
                            if (fileS.exists()) {
                                if (fileS.isFile) {
                                    doAsync {
                                        room.clearAllTables()
                                        room.close()
                                    }.get()
                                    context.applicationContext.deleteDatabase(context.getString(R.string.database_name)) // Delete current database
                                    RoomSingleton.importDB(context, File(sourceDatabase))
                                    Toast.makeText(
                                        context,
                                        R.string.successImport,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        if (!check) {
                            Toast.makeText(context, R.string.errorImport, Toast.LENGTH_SHORT).show()
                        }
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
            //} ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    fun showAlertExport(
        activity: Activity,
        context: Context,
        currentDBPath: String,
        destinationAddress: String
    ): AlertDialog {
        return activity.let {
            //return context?.let{
            //var builder = AlertDialog.Builder(it)

            //var dir = File

            var builder = AlertDialog.Builder(activity) // Builder for the dialog
            builder.setMessage(R.string.dialog_export_database)
                .setPositiveButton(R.string.exportD, //Set option for positive answer
                    DialogInterface.OnClickListener { dialog, id ->
                        // User export database
                        var directoryS = File(destinationAddress)
                        var directory =
                            File(context.getString(R.string.folder_path)) // Get folder by default
                        Log.d("Directory-->", "Dst add " + destinationAddress)
                        if (directoryS.canWrite()) {
                            Log.d("Directory-->", "Can Write")
                            if (directoryS.exists()) {
                                if (directoryS.isDirectory) {
                                    //exportDB(currentDBPath, directory.toString())
                                    exportDB(
                                        currentDBPath,
                                        destinationAddress,
                                        context.getString(R.string.database_name)
                                    )
                                    Log.d("Directory-->", "Folder exists " + directory)
                                    Toast.makeText(
                                        context,
                                        R.string.successExport,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    //Log.d("Directory-->",directory.toString())
                                    Toast.makeText(
                                        context,
                                        R.string.errorExport,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            } else {
                                //Log.d("Directory-->",directory.toString())
                                Toast.makeText(context, R.string.errorExport, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Log.d("Directory-->", "Can NOT Write")
                        }
                        //Log.d("Directory-->", "Data " + directory)
                        //Log.d("Directory-->", "Root " + diretory2)
                    })
                .setNegativeButton(R.string.cancel, //Set option for negative answer
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
                .setTitle(R.string.dialog_export_title) // Set title of the dialog
            // Create the AlertDialog object and return it
            builder.create()
            //} ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    private fun exportDB(pathSource: String, pathDestination: String, databaseName: String) {
        try {
            var fileS = FileInputStream(pathSource)
            var dummyDay = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())
            var nameOutput =
                pathDestination + dummyDay + "_" + databaseName // i.e Download/01-01-20_database.db
            Log.d("ExportDB-->", "Path source " + pathSource)
            Log.d("ExportDB-->", "Path dst " + nameOutput)

            var fileO = FileOutputStream(nameOutput) // Open an empty db

            var i: Int
            do {
                i = fileS.read()
                fileO.write(i)
            } while (i != -1)


            fileS.read()
            // Close the streams
            fileO.flush()
            fileO.close()
            fileS.close()
        } catch (e: IOException) {
            Log.d("ERROR DIRECTORY->", e.toString()) // Print error?
        }
    }
}

class ExportImportFragment : Fragment() {

    private lateinit var productDB: RoomSingleton // Access database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export_import, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productDB = RoomSingleton.getInstance(requireContext())

        var currentDBPath =
            this.requireContext().getDatabasePath(getString(R.string.database_name)).absolutePath
        Log.d("Directory-->", "DB path " + currentDBPath)

        var sourceText = view.findViewById<EditText>(R.id.editTextSource_export)
        var destinationText = view.findViewById<EditText>(R.id.editTextDestination_export)

        view.findViewById<Button>(R.id.button_export_E).setOnClickListener {

            if (destinationText.text.isEmpty()) {
                destinationText.error = getString(R.string.errorAddress)
            } else {
                AlertDialogFragment().showAlertExport(
                    this.requireActivity(),
                    this.requireContext(),
                    currentDBPath,
                    destinationText.text.toString()
                )
                    .show()
            }
        }
        view.findViewById<Button>(R.id.button_export_I).setOnClickListener {

            if (sourceText.text.isEmpty()) {
                sourceText.setError(getString(R.string.errorAddress))
            } else {
                AlertDialogFragment().showAlertImport(
                    this.requireActivity(),
                    this.requireContext(),
                    sourceText.text.toString(),
                    productDB
                ).show()
            }
        }
    }

}