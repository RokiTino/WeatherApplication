package com.example.android.myapplication.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.android.myapplication.R
import com.example.android.myapplication.data.Weather

@Composable
fun WetherCard(
    modifier: Modifier = Modifier,
    weather: Weather,
    temp: Double,
    city: String
) {

    Box(modifier=modifier) {
        Row {
            Text(
                text = city,
                textDecoration = TextDecoration.None,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                color = Color.White,
                modifier= Modifier
                    .weight(1f)
                    .padding(10.dp)
            )
            Row (modifier= Modifier
                .weight(3f)
                .align(Alignment.CenterVertically)) {

                Text(text = temp.toString() + " ˚F",
                    textDecoration = TextDecoration.None,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = Color.LightGray,
                    modifier= Modifier
                        .weight(3f)
                        .padding(10.dp))

                Row (modifier=Modifier.weight(2f)) {
                    Text(text = weather.main,
                        textDecoration = TextDecoration.None,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W100,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        modifier = Modifier
                            .weight(2f)
                            .padding(3.dp)
                    )
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${weather.icon}@2x.png",
                        contentDescription = weather.description,
                        modifier = Modifier
                            .weight(2f)
                            .padding(5.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_star_24), contentDescription = "Favorite")
                }

            }
        }
    }

}