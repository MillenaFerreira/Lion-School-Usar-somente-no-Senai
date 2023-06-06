package br.senai.sp.jandira.lion_school.model

data class Student(
    val foto : String,
    val nome : String,
    val matricula : String,
    val curso : List<Course>
)
//O QUE ESTÁ DENTRO DO ALUNO