package br.senai.sp.jandira.lion_school

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lion_school.model.Students
import br.senai.sp.jandira.lion_school.model.StudentsList
import br.senai.sp.jandira.lion_school.service.RetrofitFactory
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            LionSchoolTheme{
                val siglaCurso =intent.getStringExtra("sigla")
                StudentsScreen(siglaCurso.toString())
            }
        }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentsScreen( curso: String ) {

    //contexto atual
    val context = LocalContext.current

    var listStudents by remember{
        mutableStateOf(listOf<Students>())
    }

    var nameCourse by remember{
        mutableStateOf("")
    }

// Estado para controlar a opção selecionada
    var selectedOption by remember{mutableStateOf(1)}


    val call = RetrofitFactory().getStudentsService().getStudents(curso)

    call.enqueue(object : Callback<StudentsList> {
        override fun onResponse(call: Call<StudentsList>, response: Response<StudentsList>) {
            listStudents = response.body()!!.aluno
            nameCourse = response.body()!!.NomeCurso
        }

        override fun onFailure(call: Call<StudentsList>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush
                    .verticalGradient(
                        listOf(
                            Color(51, 71, 176),
                            Color(255, 255, 255)
                        )
                    )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(21.dp)
                    .clickable{
                        var backHome = Intent(context, CoursesActivity::class.java)

                        //start a Activity
                        context.startActivity(backHome)
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = {},
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(50.dp),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color(231, 191, 96),
//                        unfocusedBorderColor = Color(231, 191, 96),
//
//                        ),
//                    label = {
//                        Text(
//                            text = "Search...",
//                            color = Color.White
//                        )
//                    },
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(
//                                id = R.drawable.baseline_search_24
//                            ),
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                )
            }
            ToggleButton(selectedOption, onOptionSelected ={option->
                selectedOption = option
                // Lógica para tratar a opção selecionada
                when (option) {
                    1 -> {
                        // Opção 1 selecionada
                        // Faça algo aqui
                    }
                    2 -> {
                        // Opção 2 selecionada
                        // Faça algo aqui
                    }
                    3 -> {
                        // Opção 3 selecionada
                        // Faça algo aqui
                    }
                }
            })
            Text(
                text = nameCourse,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(42.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(listStudents){
                    Card(
                        modifier = Modifier
                            .height(250.dp)
                            .width(250.dp)
                            .clickable{
                                var openStudent = Intent(context, StudentActivity::class.java)
                                openStudent.putExtra("matricula",it.matricula)

                                //start a Activity
                                context.startActivity(openStudent)
                            },
                        shape =RoundedCornerShape(25.dp),
                        backgroundColor =Color(229, 182, 87),
                        elevation = 4.dp
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            AsyncImage(
                                model =it.foto,
                                contentDescription = "",
                                modifier = Modifier.size(170.dp)
                            )
                            Text(
                                text =it.nome.uppercase(),
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(42.dp))
                }

            }
        }
    }
}

@Composable
fun ToggleButton(selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ){
        val options =listOf("Todos", "Cursando", "Finalizado")
        options.forEachIndexed{index, option->
            val isSelected = index + 1 == selectedOption
            Button(
                onClick ={onOptionSelected(index + 1)},
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(horizontal = 6.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected)Color(51, 71, 176) else Color(164, 177, 248, 255),
                contentColor = if (isSelected)Color(231, 191, 96) else Color(51, 71, 176)
            )
            ){
            Text(
                text = option,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
            )
        }
        }
    }
}

