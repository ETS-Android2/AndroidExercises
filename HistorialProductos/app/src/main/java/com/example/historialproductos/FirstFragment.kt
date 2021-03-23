package com.example.historialproductos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_register).setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            findNavController().navigate(R.id.action_FirstFragment_to_FragmentRegister)
        }
        view.findViewById<Button>(R.id.button_search).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FragmentSearch)
        }
        view.findViewById<Button>(R.id.button_export).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FragmentExportImport)
        }
        view.findViewById<Button>(R.id.button_list).setOnClickListener {
            Toast.makeText(context, R.string.fixing, Toast.LENGTH_SHORT).show()
        }
    }
}