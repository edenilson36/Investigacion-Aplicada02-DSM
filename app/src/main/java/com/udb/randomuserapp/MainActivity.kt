package com.udb.randomuserapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView // Importar TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout // Importar SwipeRefreshLayout
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

    // Declarar las vistas
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewError: TextView

    private var userList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewUsers)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        progressBar = findViewById(R.id.progressBar)
        textViewError = findViewById(R.id.textViewError)

        //Configuramos RecyclerView y Adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        //Integrar la lógica del SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            loadUsers() // Recargar datos al hacer swipe
        }

        // Cargar los datos
        loadUsers()
    }

    private fun loadUsers() {
        // Implementar pantalla de carga
        // Mostrar ProgressBar solo si no estamos ya refrescando con el Swipe
        if (!swipeRefreshLayout.isRefreshing) {
            progressBar.visibility = View.VISIBLE
        }
        recyclerView.visibility = View.GONE
        textViewError.visibility = View.GONE // Ocultar cualquier error anterior

        val call: Call<RandomUserResponse> = ApiClient.randomUserApi.getUsers(20)

        call.enqueue(object : Callback<RandomUserResponse> {
            override fun onResponse(
                call: Call<RandomUserResponse>,
                response: Response<RandomUserResponse>
            ) {
                //Detener indicadores de carga
                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false // Detener el SwipeRefresh

                if (response.isSuccessful) {
                    val randomUserResponse: RandomUserResponse? = response.body()
                    randomUserResponse?.let { userResponse ->
                        // Éxito: Mostrar la lista
                        userList.clear()
                        userList.addAll(userResponse.results)
                        userAdapter.updateData(userList)
                        recyclerView.visibility = View.VISIBLE
                    }
                } else {
                    // Implementar mensaje de Error del servidor
                    handleErrorState("Error en el servidor: código ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RandomUserResponse>, t: Throwable) {
                //Detener indicadores de carga
                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false // Detener el SwipeRefresh

                //Implementar mensaje de Sin Conexión
                handleErrorState("No hay conexión. Por favor, revisa tu red.")
                Log.e(TAG, "Error en la petición: ${t.message}")
            }
        })
    }

    //Manejar el estado de error (oculta la lista y muestra el mensaje)
    private fun handleErrorState(message: String) {
        recyclerView.visibility = View.GONE
        textViewError.text = message
        textViewError.visibility = View.VISIBLE
    }
}