package com.sphe.whattoeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.sphe.whattoeat.ui.theme.WhatToEatTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatToEatTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable<ScreenA> {
            ScreenA(navController)
        }
        composable<ScreenB> {
            val args = it.toRoute<ScreenB>()
            ScreenB(navController, args.time ?: "Unknown")
        }
    }
}
@Composable
fun SplashScreen(navController: NavController) {
    var alpha by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = true) {
        alpha = 1f
        delay(1500)
        navController.navigate(ScreenA) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What To Eat?",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.alpha(alpha)
        )
    }
}

@Composable
fun ScreenA(navController: NavController) {
    var text by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("Try: Morning, Mid-morning, Afternoon, etc.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF000000)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let’s find you something to eat.",
            fontWeight = FontWeight.Medium,
            fontSize = 31.sp,
            color = Color(0xFFFCFCFC),
            lineHeight = 35.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .width(250.dp)
        )

        Column (
            modifier = Modifier
                .padding(top = 20.dp),
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(51.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = {newTime ->
                        text = newTime
                        time = newTime
                    },
                    shape = RoundedCornerShape(30.dp),
                    placeholder = {
                        Text(
                            text = "Enter a time of day",
                            color = Color(0xFFA8A8A8),
                            fontSize = 13.sp
                        )
                    },
                    textStyle = TextStyle(color = Color.White),
                    colors= OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF0F0F0F),
                        focusedContainerColor = Color(0xFF0F0F0F),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        when (time) {
                            "Morning", "Mid-Morning Snack", "Afternoon", "Dinner", "Post-Dinner" -> {
                                navController.navigate(ScreenB(
                                    time = time,
                                ))
                            }
                            else -> errorMessage = "Incorrect time of day."
                        }
                    },
                    modifier = Modifier
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add",
                        tint = Color(0xFF000000),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Search",
                        color = Color(0xFF000000)
                    )
                }
            }

            Row (
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    errorMessage,
                    color = Color(0xFFFFFFFF),
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun ScreenB(navController: NavController, time: String) {
    ScaffoldScreenB(navController, time)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreenB(navController: NavController, time: String) {
    var foodName by remember { mutableStateOf("") }
    var foodDescription by remember { mutableStateOf("") }
    var foodPrepTime by remember { mutableStateOf("") }
    var foodRating by remember { mutableStateOf("") }
    var foodImage by remember { mutableStateOf(R.drawable.unknown) }

    LaunchedEffect(time) {
        val selectedFood = when (time) {
            "Morning" -> listOf(
                Triple("Eggs", "A nutritious and versatile staple, packed with protein and healthy fats. Perfect for a quick and satisfying meal.", R.drawable.eggs) to Pair("10 min", "4.8"),
                Triple("Oatmeal", "A warm and hearty breakfast option made from oats, often topped with fruits, nuts, or honey for extra flavor.", R.drawable.oatmeal) to Pair("5 min", "4.5"),
                Triple("Pancakes", "Fluffy and delicious, pancakes are a breakfast classic, often enjoyed with syrup, butter, or fruit toppings.", R.drawable.pancakes) to Pair("15 min", "4.9"),
                Triple("Toast & Avocado", "A simple yet nutritious breakfast, avocado toast is packed with healthy fats and fiber, often garnished with seasonings or eggs.", R.drawable.avocado_toast) to Pair("7 min", "4.7"),
                Triple("Smoothie", "A refreshing and nutritious drink made by blending fruits, yogurt, or milk, perfect for a quick and healthy breakfast.", R.drawable.smoothie) to Pair("3 min", "4.6")
            ).random()

            "Mid-Morning Snack" -> listOf(
                Triple("Banana", "A naturally sweet and energy-boosting fruit rich in potassium and vitamins, perfect for a quick snack.", R.drawable.banana) to Pair("1 min", "4.5"),
                Triple("Granola Bar", "A compact, nutritious snack made from oats, nuts, and honey, providing a quick energy boost.", R.drawable.granola_bar) to Pair("2 min", "4.6"),
                Triple("Yogurt", "A creamy and probiotic-rich dairy snack, often enjoyed with fruit or granola for added flavor and texture.", R.drawable.yogurt) to Pair("3 min", "4.7"),
                Triple("Nuts & Raisins", "A protein-packed snack mix that offers healthy fats, fiber, and natural sweetness.", R.drawable.nuts_raisins) to Pair("0 min", "4.4"),
                Triple("Apple Slices & Peanut Butter", "A delicious combination of crisp apples and protein-rich peanut butter, balancing sweetness and creaminess.", R.drawable.apple_peanutbutter) to Pair("4 min", "4.8")
            ).random()

            "Afternoon" -> listOf(
                Triple("Sandwich", "A quick and filling meal with various ingredients between slices of bread, perfect for lunch on the go.", R.drawable.sandwich) to Pair("10 min", "4.7"),
                Triple("Pizza", "A cheesy, flavorful delight with various toppings, loved by many as a satisfying lunch option.", R.drawable.pizza) to Pair("20 min", "4.9"),
                Triple("Burger", "A juicy and hearty meal with a grilled patty, fresh veggies, and condiments in a bun.", R.drawable.burger) to Pair("15 min", "4.8"),
                Triple("Pasta", "A comforting and delicious dish with various sauces and toppings, perfect for a midday meal.", R.drawable.pasta) to Pair("25 min", "4.6"),
                Triple("Rice & Chicken", "A well-balanced meal providing protein and carbohydrates, making it a filling and nutritious choice.", R.drawable.rice_chicken) to Pair("30 min", "4.9")
            ).random()

            "Dinner" -> listOf(
                Triple("Pasta", "A rich and comforting dish with various sauces, cheeses, and meats, making it a favorite dinner choice.", R.drawable.pasta) to Pair("25 min", "4.8"),
                Triple("Steak & Vegetables", "A protein-rich dish paired with roasted or steamed vegetables for a balanced meal.", R.drawable.steak_veggies) to Pair("35 min", "5.0"),
                Triple("Grilled Chicken", "A flavorful and healthy protein choice, often served with salads or sides.", R.drawable.grilled_chicken) to Pair("30 min", "4.9"),
                Triple("Soup", "A warm and nourishing dish, perfect for a light yet satisfying dinner.", R.drawable.soup) to Pair("20 min", "4.7"),
                Triple("Sushi", "A Japanese delicacy featuring rice, seafood, and vegetables, known for its fresh flavors.", R.drawable.sushi) to Pair("40 min", "4.6")
            ).random()

            "Post-Dinner" -> listOf(
                Triple("Ice Cream", "A creamy and delightful frozen treat available in various flavors, perfect for dessert.", R.drawable.ice_cream) to Pair("5 min", "4.8"),
                Triple("Chocolate Cake", "A rich and decadent dessert loved by many for its deep chocolate flavor.", R.drawable.chocolate_cake) to Pair("30 min", "4.9"),
                Triple("Fruit Salad", "A refreshing mix of fresh fruits, providing natural sweetness and vitamins.", R.drawable.fruit_salad) to Pair("10 min", "4.7"),
                Triple("Cheesecake", "A smooth and creamy dessert with a graham cracker crust, often topped with fruit or chocolate.", R.drawable.cheesecake) to Pair("35 min", "4.6"),
                Triple("Brownie", "A dense and fudgy chocolate treat, perfect for satisfying sweet cravings.", R.drawable.brownie) to Pair("25 min", "4.8")
            ).random()

            else -> Triple("Unknown", "No food information available.", R.drawable.unknown) to Pair("N/A", "N/A")
        }

        foodName = selectedFood.first.first
        foodDescription = selectedFood.first.second
        foodImage = selectedFood.first.third
        foodPrepTime = selectedFood.second.first
        foodRating = selectedFood.second.second
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF000000),
                    titleContentColor = Color(0xFFFCFCFC),
                ),
                title = {
                    Text(text = "$time", fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    Button(
                        onClick = {
                            navController.navigate(ScreenA)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),

                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color(0xFFFFFFFF),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Image (
                painter = painterResource(id = foodImage),
                contentDescription = "Eggs image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(350.dp)
                    .height(600.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 20.dp, end = 30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = foodName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    color = Color(0xFFFCFCFC)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Row (
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(color = Color(0xFF0F0F0F))
                            .padding(start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Back",
                            tint = Color(0xFFA8A8A8),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = foodPrepTime,
                            fontSize = 13.sp,
                            color = Color(0xFFA8A8A8),
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Row (
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(color = Color(0xFF0F0F0F))
                            .padding(start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Back",
                            tint = Color(0xFFA8A8A8),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = foodRating,
                            fontSize = 13.sp,
                            color = Color(0xFFA8A8A8),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = foodDescription,
                    fontSize = 13.sp,
                    color = Color(0xFFFCFCFC),
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp)
                )
            }
        }
    }
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val time: String
)

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    WhatToEatTheme {
        val screen = "Screena"
        val navController = rememberNavController()

        if (screen == "Screena") {
            ScreenA(navController  )
        } else {
            ScreenB(navController, "Afternoon")
        }

    }
}