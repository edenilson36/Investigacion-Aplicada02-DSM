package com.udb.randomuserapp

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udb.randomuserapp.adapter.UserAdapter
import com.udb.randomuserapp.api.ApiClient
import com.udb.randomuserapp.model.RandomUserResponse
import com.udb.randomuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    // declaramos las vistas a nivel de clase
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var userList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewUsers)

        // Configuramos RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // inicializamos el adapter con una lista vacia
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter
        //Probar consumo de la api
        loadUsers()
    }

    private fun loadUsers() {
        val call: Call<RandomUserResponse> = ApiClient.randomUserApi.getUsers(20)

        call.enqueue(object : Callback<RandomUserResponse> {
            override fun onResponse(
                call: Call<RandomUserResponse>,
                response: Response<RandomUserResponse>
            ) {
                if (response.isSuccessful) {
                    val randomUserResponse: RandomUserResponse? = response.body()
                    randomUserResponse?.let { userResponse ->
                        //Conectar la lista de datos al Adapter
                        userList.clear()
                        userList.addAll(userResponse.results)
                        userAdapter.updateData(userList) // Usamos la función del Adapter

                        Log.d(TAG, "Usuarios obtenidos y mostrados: ${userResponse.results.size}")
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RandomUserResponse>, t: Throwable) {
                Log.e(TAG, "Error en la petición: ${t.message}")
            }
        })
    }
}