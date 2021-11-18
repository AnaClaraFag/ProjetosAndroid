package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCalcular.setOnClickListener{calcularGorjeta()}
    }

    private fun calcularGorjeta() {
        val custoViraNum = binding.custoDoServico.text.toString()
        val custo = custoViraNum.toDouble()
        val selecaoId = binding.opcoesGorjeta.checkedRadioButtonId
        val porcentagem = when(selecaoId){
            R.id.opcao_20porCento -> 0.2
            R.id.opcao_18porCento -> 0.18
            else -> 0.15
        }
        var gorjeta = porcentagem * custo
        val arrendonda = binding.arredondar.isChecked

        if (arrendonda){
            gorjeta = ceil(gorjeta)
        }

        val formataGorjeta = NumberFormat.getCurrencyInstance().format(gorjeta)
        binding.resultado.text = getString(R.string.valor_da_gorjeta, formataGorjeta)
    }
}