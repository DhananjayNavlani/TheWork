package com.example.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.shoppingcart.placeholder.PlaceholderContent
import com.example.shoppingcart.placeholder.PlaceholderContent.PlaceholderItem
import java.util.Locale

class CheckoutViewModel(id:Int, initialQuantity:Int): ViewModel() {
/*  //Using raw viewModel
    val product = PlaceholderContent.ITEMS.find { it.id == id }

    private var _qty = initialQuantity
    val qty get() = _qty

    fun addQty(qty:Int){
        _qty+=qty
    }
    fun reduceQty(qty:Int) {
        if ((_qty - qty) > 0) {
            _qty -= qty
        }
    }
*/

    //Using viewModel with Livedata
    private val _qty = MutableLiveData<Int>(initialQuantity)
    val qty: LiveData<Int>
        get() = _qty

    private val _product = MutableLiveData<PlaceholderItem>(PlaceholderContent.ITEMS.find { it.id == id })
    val product: LiveData<PlaceholderItem>
        get() = _product
    
    val trimDesc: LiveData<String> = _product.map(::trimm)

    fun trimm(product: PlaceholderItem):String{
        return product.longDescription.substring(0,50).uppercase(Locale.getDefault())
    }
    fun addQty(newQty:Int){
        _qty.value?.let {
            _qty.value = it + newQty
        }
    }

    fun reduceQty(newQty: Int){
        _qty.value?.let {
            if((it - newQty)>0){
                _qty.value = it - newQty
            }
        }
    }
    private val _price: MutableLiveData<Float> = _product.map { it.price } as MutableLiveData<Float>
    val price: LiveData<Float>
        get() = _price

    private val _discountApplied = _product.map { it.discountApplied } as MutableLiveData<Boolean>
    val discountApplied: LiveData<Boolean>
        get() = _discountApplied

    fun applyDiscount(percent: Float){
        _product.value?.let {
            it.price -= it.price*(percent/100)
            it.discountApplied = true
        }

        _price.value?.let {
            _price.value = it - (it * (percent/100))
        }
//        _price.value = _price.value?.minus(_price.value!! * (percent/100))
        _discountApplied.value?.let {
            _discountApplied.value = true
        }
    }

}