package fes.aragon.listausuarios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import fes.aragon.listausuarios.databinding.UserItemBinding

class UsuarioAdapter(private val usuario: List<usuario>, private val oyente:OnClickListener):RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {
    private lateinit var context:Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var binding = UserItemBinding.bind(view)

        fun oyente(usuario: usuario) {

            binding.root.setOnClickListener {
                oyente.onClick(usuario)
            }
        }
    }
    //nos ayuda a inflar la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val vista = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return usuario.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuario.get(position)
        with(holder) {
            binding.nombreCliente.text = usuario.nombre
            binding.numCliente.text=usuario.id.toString()
            oyente(usuario)
            Glide.with(context)
                .load(usuario.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.foto)
        }
    }


}