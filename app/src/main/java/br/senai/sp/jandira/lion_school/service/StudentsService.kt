package br.senai.sp.jandira.lion_school.service

import br.senai.sp.jandira.lion_school.model.StudentsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StudentsService {

    //url padrão
    //https://tired-slug-hat.cyclic.app/v1/lion-school/

    //https://tired-slug-hat.cyclic.app/v1/lion-school/alunos?curso=ds
    @GET("alunos")
    fun getStudents (@Query("curso") siglaCurso : String): Call<StudentsList>
}