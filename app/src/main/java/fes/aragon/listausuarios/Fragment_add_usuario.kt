package fes.aragon.listausuarios

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.io.File
import java.io.FileWriter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_add_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_add_usuario : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var botonSalir: Button
    private lateinit var botonEscribir: Button
    private lateinit var id: TextView
    private lateinit var nombre: TextView
    private lateinit var url: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_usuario, container, false)
        id = view.findViewById(R.id.id)
        nombre = view.findViewById(R.id.nombre)
        url = view.findViewById(R.id.url)
        botonSalir = view.findViewById(R.id.cancelar)
        botonSalir.setOnClickListener {
            dismiss()
        }
        botonEscribir = view.findViewById(R.id.almacenar)
        botonEscribir.setOnClickListener {
            val sd = botonSalir.context.filesDir
            val rutaLeer = File(sd.path.toString(), "usuario.txt")
            try {
                FileWriter(rutaLeer, true).use {
                    val datos = java.lang.StringBuilder()
                    datos.append(id.text)
                        .append(";")
                        .append(nombre.text)
                        .append(";")
                        .append(url.text)
                        .append("\n")
                    it.write(datos.toString())
                    it.close()
                    dismiss()
                }
            } catch (e: java.lang.Exception) {
                Toast.makeText(botonSalir.context, "Error al leer archivo", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_add_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_add_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}