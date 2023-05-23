package br.senai.sp.jandira.lion_school

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lion_school.CoursesActivity
import br.senai.sp.jandira.lion_school.R
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {

    //contexto atual
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush
                    .verticalGradient(
                        listOf(
                            Color(51, 68, 176),
                            Color(255, 255, 255)
                        )
                    )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(42.dp)
                        .height(52.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Lion School",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontFamily = FontFamily.Serif
                )
            }
            Image(
                painter = painterResource(
                    id = R.drawable.student
                ),
                contentDescription = "",
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(200.dp)
                    .height(400.dp)
            )
            Button(
                onClick = {
                    var openCourses = Intent(context, CoursesActivity::class.java)

                    //start a Activity
                    context.startActivity(openCourses)

                },
                colors = ButtonDefaults.buttonColors(Color(231, 191, 96)),
                modifier = Modifier.width(200.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Start",
                        fontSize = 32.sp,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_arrow_forward_24
                        ),
                        contentDescription = "",
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                }

            }
        }
    }
}