package br.senai.sp.jandira.lion_school

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lion_school.model.Student
import br.senai.sp.jandira.lion_school.service.RetrofitFactory
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val matriculaAluno = intent.getStringExtra("matricula")
                StudentScreen(matriculaAluno.toString())
            }
        }
    }
}


@Composable
fun StudentScreen(matricula: String) {
    val context = LocalContext.current

    var student by remember {
        mutableStateOf(Student("", "", "", emptyList()))
    }

    val call = RetrofitFactory().getStudentsService().getStudentRegistration(matricula)

    call.enqueue(object : Callback<Student> {
        override fun onResponse(call: Call<Student>, response: Response<Student>) {
            if (response.isSuccessful) {
                val studentResponse = response.body()
                if (studentResponse != null) {
                    student = studentResponse
                }
            } else {
                Log.e("teste", "Erro na resposta da API: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Student>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(51, 71, 176),
                        Color(255, 255, 255)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {}
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(student.curso) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = student.foto,
                                contentDescription = "",
                                modifier = Modifier.size(230.dp)
                            )

                            Text(
                                text = student.nome,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(20.dp),
                            shape = RoundedCornerShape(25.dp),
                            backgroundColor = Color.White
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(student.curso[0].disciplinas) {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    var barra = 2.4 * it.media.toDouble()
                                    var corBarra = Color.White

                                    if (it.media.toDouble() > 60) {
                                        corBarra = Color(116, 131, 239)
                                    } else if (it.media.toDouble() < 60 && it.media.toDouble() >= 50) {
                                        corBarra = Color(229, 182, 87)
                                    } else {
                                        corBarra = Color(239, 116, 116)
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(240.dp)
                                            .height(40.dp)
                                    ) {
                                        Text(
                                            text = it.nome,
                                            fontWeight = FontWeight(700),
                                            fontSize = 12.sp,
                                            color = colorResource(id = R.color.black)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .height(17.5.dp)
                                                .width(240.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(
                                                    Color.Black
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(
                                                        corBarra
                                                    )
                                                    .width(barra.dp)
                                                    .padding(0.dp, 0.dp, 5.dp, 0.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Text(
                                                    text = it.media + "%",
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 12.sp,
                                                    color = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}