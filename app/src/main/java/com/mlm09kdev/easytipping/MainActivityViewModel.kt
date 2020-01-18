package com.mlm09kdev.easytipping

import androidx.annotation.NonNull
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


class MainActivityViewModel : ViewModel() {


    @Bindable
    val _billSubTotal = MutableLiveData<String>()

    @NonNull
    val billSubTotal = Transformations.map(_billSubTotal) { it.toFloatOrNull() ?: 0.0f }

    @Bindable
    val tipPercentValue = MutableLiveData(15)

    @Bindable
    val splitValue = MutableLiveData(1)


    private val _totalBillAmount = MutableLiveData<Float>()
    val totalBillAmount: LiveData<Float>
        get() = _totalBillAmount

    private val _totalTipAmount = MutableLiveData<Float>()
    val totalTipAmount: LiveData<Float>
        get() = _totalTipAmount

    private val _tipTotalPerPersonAmount = MutableLiveData<Float>()
    val tipTotalPerPersonAmount: LiveData<Float>
        get() = _tipTotalPerPersonAmount

    private val _totalBillPerPersonAmount = MutableLiveData<Float>()
    val totalBillPerPersonAmount: LiveData<Float>
        get() = _totalBillPerPersonAmount

    fun init() {
        _totalBillAmount.value = 0.0f
        _totalTipAmount.value = 0.0f
        _tipTotalPerPersonAmount.value = 0.0f
        _totalBillPerPersonAmount.value = 0.0f
    }

    private fun calcTotalTip() {
        _totalTipAmount.value = tipPercentValue.value?.times(_totalBillAmount.value!!)?.div(100)?: 0.00f
    }

    private fun calcTipPerPerson() {
        _tipTotalPerPersonAmount.value = _totalTipAmount.value?.div(splitValue.value!!)?: 0.00f

    }

    private fun calcTotalPerPerson() {

          _totalBillPerPersonAmount.value = billSubTotal.value?.plus(_totalTipAmount.value!!)?.div(splitValue.value!!) ?: 0.00f
    }

    private fun setTotalBill() {
        _totalBillAmount.value = (billSubTotal.value) ?: 0.00f
    }

    fun calcTotals() {
        setTotalBill()
        calcTotalTip()
        calcTipPerPerson()
        calcTotalPerPerson()
    }


}
