package fes.aragon.listausuarios


import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fes.aragon.listausuarios.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity(), OnClickListener,
    DialogInterface.OnDismissListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var id : String
    private lateinit var nombre : String
    private lateinit var url : String

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted ->
            val message= if(isGranted) "Permiso Aceptado" else " Permiso Rechazado"
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        inicioCodigo()
    }

    private fun generarDatos():MutableList<usuario> {
        val usuarios = mutableListOf<usuario>()
        var us:usuario
        val sd = binding.recyclerview.context.filesDir
        val rutaLeer = File(sd.path.toString(),"usuario.txt")
        if(rutaLeer.exists()){
            println("Aqui esta el path " + sd.path)
            var buffer = BufferedReader(FileReader(rutaLeer))
            try {
                buffer.lines().forEach(){
                    var datos = it.split(";")
                    us = usuario(datos.get(0).toInt(), datos.get(1),datos.get(2))
                    usuarios.add(us)
                }
            }catch (e:java.lang.Exception){
                Toast.makeText(this,"Error al leer archivo", Toast.LENGTH_SHORT).show()
                println(e)
            }
        }else{
            File(sd.path.toString(),"usuario.txt")
            Toast.makeText(this,"No hay datos que leer", Toast.LENGTH_SHORT).show()
        }
        return usuarios
    }

    override fun onClick(usuario: usuario) {
        id = usuario.id.toString()
        nombre = usuario.nombre
        url = usuario.url
        Toast.makeText(this, usuario.id.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun inicioCodigo(){
        iniciarComponentes()

        binding.agregar.setOnClickListener{
            println("Evento Corre")
            var fr = Fragment_add_usuario();
            fr.isCancelable = false
            fr.show(supportFragmentManager,"Datos de entrada")
        }

        binding.eliminar.setOnClickListener{
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
            if (id != null && id != "-1") {
                var fr = Fragment_delete_usuario.newInstance(id, nombre, url);
                fr.isCancelable = false
                fr.show(supportFragmentManager,"Eliminar")
                id = "-1"
            }
        }
    }

    private fun iniciarComponentes(){
        usuarioAdapter = UsuarioAdapter(generarDatos(),this)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerview.apply {
            layoutManager = linearLayoutManager
            adapter=usuarioAdapter
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        iniciarComponentes()
    }

    /*private fun checarPermisos(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            pedirPermisoLectura()
        }else{
            inicioCodigo()
        }
    }

    private fun pedirPermisoLectura(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this,"Permiso rechazado",Toast.LENGTH_SHORT)
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }*/
}