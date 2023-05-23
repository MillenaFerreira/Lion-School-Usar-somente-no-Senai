package br.senai.sp.jandira.lion_school

import android.content.Intent
import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.lion_school.ui.theme.LionSchoolTheme

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                StudentsScreen()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun StudentsScreen() {

    //contexto atual
    val context = LocalContext.current

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
                    var backHome = Intent(context, CoursesActivity::class.java)

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

        }
    }
}