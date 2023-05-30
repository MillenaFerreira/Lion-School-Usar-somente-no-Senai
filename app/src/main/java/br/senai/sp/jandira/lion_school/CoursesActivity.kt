package br.senai.sp.jandira.lion_school

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lion_school.model.CoursesList
import br.senai.sp.jandira.lion_school.service.RetrofitFactory
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                CoursesScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoursesScreen() {

    //contexto atual
    val context = LocalContext.current

    var listCourses by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lion_school.model.Courses>())
    }

    //Cria uma chamada para o endpoint
    val call = RetrofitFactory().getCoursesService().getCourses()

    call.enqueue(object : Callback<CoursesList>{
        override fun onResponse(call: Call<CoursesList>, response: Response<CoursesList>) {
            listCourses = response.body()!!.cursos
            Log.i("ds2t", "onResponse: ${response.body()!!.cursos}")
        }

        override fun onFailure(call: Call<CoursesList>, t: Throwable) {
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
                            Color(0, 91, 234),
                            Color(255, 255, 255, 255)
                        )
                    )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    var backHome = Intent(context, MainActivity::class.java)

                    //start a Activity
                    context.startActivity(backHome)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(231, 191, 96),
                        unfocusedBorderColor = Color(231, 191, 96),

                    ),
                    label = {
                        Text(
                            text = "Search...",
                            color = Color.White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_search_24
                            ),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row() {
                Text(
                    text = "Escolha um curso para gerenciar",
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 58.sp
                )
//                Text(text = "curso ", fontSize = 36.sp, color = Color.Blue)
//                Text(text = "para gerenciar", fontSize = 36.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(listCourses){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(start = 31.dp, end = 31.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(228, 228, 228, 228),
                                        Color(255, 255, 255, 1)
                                    ),
                                )
                            ) ){
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    var openStudents = Intent(context, StudentsActivity::class.java)
                                    openStudents.putExtra("sigla", it.sigla)

                                    //start a Activity
                                    context.startActivity(openStudents)
                                },
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = it.icone,
                                    contentDescription = "",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Column() {
                                    Text(
                                        text = it.sigla ?: "teste",
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = it.nome ?: "teste",
                                        fontSize = 12.sp
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(
                                            text = it.carga + "h",
                                            fontSize = 8.sp
                                        )
                                    }
                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                }
            }

        }
    }
}



