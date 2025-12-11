package com.yarasa.netflux.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.yarasa.netflux.model.Expense
import com.yarasa.netflux.model.Income
import com.yarasa.netflux.model.Transaction
import com.yarasa.netflux.ui.theme.NetfluxTheme
import java.math.BigDecimal
import androidx.compose.ui.graphics.shadow.Shadow
import java.math.RoundingMode

@Composable
fun MainScreen(transaction: List<Transaction>){
    LazyColumn(modifier = Modifier.fillMaxWidth()
        .padding(5.dp)
        .background(Color(0xFF000805))
    ) {
        items(transaction){
            when (it) {
                is Expense -> ExpenseItem(expense = it)
                is Income -> IncomeItem(income = it)
                else -> {}
            }

        }

    }
}

@Composable
fun ExpenseItem(expense: Expense){

    val para = expense.value
    val yaklasikPara = para.setScale(2, RoundingMode.HALF_DOWN)

    Column(modifier = Modifier.fillMaxWidth()
        .padding(5.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFE33E33))
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = expense.category+ " ",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )

                Text(text = " " + expense.title,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(text = "-" + yaklasikPara.toString(),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Thin,
                color = Color.White
            )
        }

    }


}

@Composable
fun IncomeItem(income: Income){

    val para = income.value
    val yaklasikPara = para.setScale(2, RoundingMode.HALF_DOWN)

    Column(modifier = Modifier.fillMaxWidth()
        .padding(5.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFF228B22))
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = income.source + " ",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )

                Text(text = " " + income.title,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(text = "+" + yaklasikPara.toString(),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Thin,
                color = Color.White
            )


        }

    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    NetfluxTheme {
        val para = BigDecimal(100.323)
        val gider  = Expense(para,"Kira",1,1,"yaşam")
        val gelir  = Income(para,"Burs",1,1,"KYK")
        val transaction = listOf(gider,gelir,gider,gelir,gider,gelir,gider,gelir)
        MainScreen(transaction = transaction)
    }
}