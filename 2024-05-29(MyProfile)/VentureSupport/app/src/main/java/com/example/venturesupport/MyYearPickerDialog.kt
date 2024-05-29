package com.example.venturesupport

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.venturesupport.databinding.YearPickerBinding
import java.util.Calendar

class MyYearPickerDialog : DialogFragment() {
    private val binding: YearPickerBinding by lazy {
        YearPickerBinding.inflate(layoutInflater)
    }

    private val MAX_YEAR = 2099
    private val MIN_YEAR = 1980

    private var listener: DatePickerDialog.OnDateSetListener? = null
    private val cal = Calendar.getInstance()

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialogView = binding.root

        with(binding) {
            btnCancel.setOnClickListener {
                dialog?.cancel()
            }

            btnConfirm.setOnClickListener {
                listener?.onDateSet(null, pickerYear.value, 0, 0)
                dialog?.cancel()
            }

            val year = cal.get(Calendar.YEAR)
            pickerYear.minValue = MIN_YEAR
            pickerYear.maxValue = MAX_YEAR
            pickerYear.value = year
        }

        builder.setView(dialogView)

        return builder.create()
    }
}