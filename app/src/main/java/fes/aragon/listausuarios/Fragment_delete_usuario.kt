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
import androidx.fragment.app.DialogFragment
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [SeguroBorrarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_delete_usuario : DialogFragment() {
    // TODO: Rename and change types of parameters
    private lateinit var id: String
    private lateinit var nombre: String
    private lateinit var url: String
    private lateinit var botonSalir: Button
    private lateinit var botonBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_PARAM1).toString()
            nombre = it.getString(ARG_PARAM2).toString()
            url = it.getString(ARG_PARAM3).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_delete_usuario, container, false)
        botonSalir = view.findViewById(R.id.cancelar)
        botonSalir.setOnClickListener {
            dismiss()
        }
        botonBorrar = view.findViewById(R.id.borrar)
        botonBorrar.setOnClickListener{
            val usuarios = mutableListOf<String>()
            val rutaLeer = File("/storage/emulated/0/Music","usuario.txt")
            if(rutaLeer.exists()){
                var buffer = BufferedReader(FileReader(rutaLeer))
                try {
                    buffer.lines().forEach(){
                        if (it.toString() != "") {
                            var datos = it.split(";")
                            usuarios.add(it)
                        }
                    }
                    val indiceAEliminar = usuarios.indexOfFirst { it.toString().startsWith("${id};") }
                    if (indiceAEliminar != -1) {
                        usuarios.removeAt(indiceAEliminar)
                        FileWriter(rutaLeer,false).use {
                            val datos = java.lang.StringBuilder()
                            for (us in usuarios) {
                                datos.append("$us\n")
                            }
                            it.write(datos.toString())
                            it.close()
                        }
                    }
                    dismiss()
                }catch (e:java.lang.Exception){

                }
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
         * @return A new instance of fragment SeguroBorrarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(iden: String, nombre: String, url : String) =
            Fragment_delete_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, iden)
                    putString(ARG_PARAM2, nombre)
                    putString(ARG_PARAM3, url)
                }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity?=activity
        if (activity is DialogInterface.OnDismissListener){
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}