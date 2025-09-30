package com.udb.randomuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udb.randomuserapp.R
import com.udb.randomuserapp.model.User

class UserAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProfile: ImageView = itemView.findViewById(R.id.imageViewProfile)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
    }

    // Para inflar el layout del ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Asignamos los datos a las vistas y la carga de imágenes
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        // Asignamos texto
        holder.textViewName.text = user.name.getFullName()
        holder.textViewEmail.text = user.email

        // Cargamos las imagenes con Glide
        Glide.with(holder.itemView.context)
            .load(user.picture.medium)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageViewProfile)
    }

    // Devolvemos el número total de ítems
    override fun getItemCount(): Int = userList.size

    // funcion para actualizar la lista de usuarios después de una carga o un "swipe to refresh".
    fun updateData(newUsers: List<User>) {
        userList = newUsers
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }
}

