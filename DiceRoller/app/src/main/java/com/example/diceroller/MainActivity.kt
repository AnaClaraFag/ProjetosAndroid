package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

/*Lançar um dado e imprimir o resultado na tela*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botaoLancar: Button = findViewById(R.id.buttonClick)
        //val retorno = findViewById<TextView>(R.id.Retorno)
        botaoLancar.setOnClickListener {
            //retorno.text = "O dado foi lançado! (6)"

            jogaDado()
            val toast = Toast.makeText(this, "Dado lançado", Toast.LENGTH_SHORT)
            toast.show()

        }
    }

    private fun jogaDado() {
        val dado = Dado(6)
        val resultDado = dado.lancar()
        val dadoImg: ImageView = findViewById(R.id.imageView)

        when(resultDado){
            1 -> dadoImg.setImageResource(R.drawable.dice_1)
            2 -> dadoImg.setImageResource(R.drawable.dice_2)
            3 -> dadoImg.setImageResource(R.drawable.dice_3)
            4 -> dadoImg.setImageResource(R.drawable.dice_4)
            5 -> dadoImg.setImageResource(R.drawable.dice_5)
            6 -> dadoImg.setImageResource(R.drawable.dice_6)
        }
    }
}
class Dado(private val numLados: Int) {

    fun lancar(): Int {
        //limita o lançamento aleatorio do dado pelo numero de lados que são 6
        return (1..numLados).random()
    }
}