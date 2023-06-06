package br.senai.sp.jandira.lion_school

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lion_school.model.Student
import br.senai.sp.jandira.lion_school.model.StudentRegistration
import br.senai.sp.jandira.lion_school.model.StudentsList
import br.senai.sp.jandira.lion_school.service.RetrofitFactory
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme
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
fun StudentScreen(matricula : String) {

    Log.i("TAG", "StudentScreen: $matricula")

    //contexto atual
    val context = LocalContext.current

    var alunoState by remember { mutableStateOf(
        StudentRegistration(
            aluno = Student("", "", "", emptyList())
        )
    ) }

    val call = RetrofitFactory().getStudentsService().getStudentRegistration(matricula)

    call.enqueue(object : Callback<StudentRegistration> {
        override fun onResponse(
            callback: Call<StudentRegistration>,
            response: Response<StudentRegistration>
        ) {
            alunoState = response.body()!!
        }
        override fun onFailure(call: Call<StudentRegistration>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

    Log.i("tag", "onResponse: ${alunoState.aluno.curso}")
    Log.i("tag", "onResponse: ${alunoState.aluno}")


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
                modifier = Modifier
                    .size(21.dp)


            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue, shape = RoundedCornerShape(25.dp))
                    .padding(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "Hélida Bento de Oliveira Liz".uppercase(),
                    color = Color.White,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(380.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(alunoState.aluno.curso[0].disciplinas){
                            var barra = 2.4 * it.media.toDouble()
                            Column(modifier = Modifier
                                .width(240.dp)
                                .height(40.dp)
                            ) {
                                Text(
                                    text = it.nome,
                                    fontWeight = FontWeight(700),
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .height(17.5.dp)
                                        .width(240.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            Color(164, 177, 248, 255)
                                        )
                                ){
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                Color(231, 191, 96)
                                            )
                                            .width(barra.dp)
                                            .padding(0.dp, 0.dp, 5.dp, 0.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ){
                                        Text(
                                            text = it.media + "%",
                                            fontWeight = FontWeight(700),
                                            fontSize = 12.sp,
                                            color = Color.White
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

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DefaultPreview2() {
//    LionSchoolTheme {
//        StudentScreen("20151001016")
//    }
//}