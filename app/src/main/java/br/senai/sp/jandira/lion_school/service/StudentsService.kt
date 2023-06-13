package br.senai.sp.jandira.lion_school.service

import br.senai.sp.jandira.lion_school.model.Student
import br.senai.sp.jandira.lion_school.model.StudentsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentsService {

    @GET("alunos")
    fun getStudents (@Query("curso") siglaCurso : String): Call<StudentsList>

    @GET("alunos/{matricula}")
    fun getStudentRegistration (@Path("matricula") matricula : String): Call<Student>

    @GET("alunos")
    fun getCourseStudentWithStatus(@Query("curso") curso: String, @Query("status") status: String): Call<StudentsList>
}