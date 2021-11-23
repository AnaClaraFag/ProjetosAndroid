package com.example.cupcake.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


private val PRICE_PER_CUPCAKE = 2.00
private val PRICE_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {
    private val _qtdOrder = MutableLiveData<Int>()
    var qtdOrder: LiveData<Int> = _qtdOrder
    private val _flavorCupCake = MutableLiveData<String> ()
    var flavor: LiveData<String> = _flavorCupCake
    private val _date = MutableLiveData<String>()
    var date:LiveData<String> = _date
    private val _price = MutableLiveData<Double>()
    var price:LiveData<String> = Transformations.map(_price){
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()


   fun setQtd (numCupCakes: Int){
       _qtdOrder.value = numCupCakes
       updatePrice()
   }

    fun setFlavor (desiredFlavor: String){
       _flavorCupCake.value = desiredFlavor
    }

    fun setDate(pickupDate:String){
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun resetOrder(){
        _qtdOrder.value = 0
        _flavorCupCake.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    init{
        resetOrder()
    }

    private fun updatePrice(){
        var calculaPrice = (qtdOrder.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0]== _date.value){
            calculaPrice += PRICE_SAME_DAY_PICKUP
        }
        _price.value = calculaPrice
    }
}