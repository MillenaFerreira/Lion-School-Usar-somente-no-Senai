package br.senai.sp.jandira.lion_school.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitFactory {
    //guarda a url Principal
    private val BASE_URL = "https://tired-slug-hat.cyclic.app/v1/lion-school/"

    //variavel que guarda como chegar no servidor (fazer a fabrica de conexao) (se conectar com a api)
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCoursesService() : CoursesService {
        return retrofitFactory.create(CoursesService::class.java)
    }

}