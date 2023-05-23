package br.senai.sp.jandira.lion_school.service

import br.senai.sp.jandira.lion_school.model.CoursesList
import retrofit2.Call
import retrofit2.http.GET

interface CoursesService {

    //url padrão
    //https://tired-slug-hat.cyclic.app/v1/lion-school/

    //https://tired-slug-hat.cyclic.app/v1/lion-school/cursos
    @GET("cursos")
    fun getCourses(): Call<CoursesList>
}