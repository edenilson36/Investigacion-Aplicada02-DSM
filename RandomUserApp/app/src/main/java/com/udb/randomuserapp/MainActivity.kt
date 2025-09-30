package com.udb.randomuserapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udb.randomuserapp.api.ApiClient
import com.udb.randomuserapp.model.RandomUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Probar el consumo de API
        testApiCall()
    }

    private fun testApiCall() {
        val call: Call<RandomUserResponse> = ApiClient.randomUserApi.getUsers(10)

        call.enqueue(object : Callback<RandomUserResponse> {
            override fun onResponse(
                call: Call<RandomUserResponse>,
                response: Response<RandomUserResponse>
            ) {
                if (response.isSuccessful) {
                    val randomUserResponse: RandomUserResponse? = response.body()
                    randomUserResponse?.let { userResponse ->
                        Log.d(TAG, "Usuarios obtenidos: ${userResponse.results.size}")

                        // Mostrar información de cada usuario en el Log
                        userResponse.results.forEach { user ->
                            Log.d(TAG, "Usuario: ${user.name.getFullName()}")
                            Log.d(TAG, "Email: ${user.email}")
                            Log.d(TAG, "Dirección: ${user.location.getFullAddress()}")
                            Log.d(TAG, "Foto: ${user.picture.medium}")
                            Log.d(TAG, "-------------------")
                        }
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