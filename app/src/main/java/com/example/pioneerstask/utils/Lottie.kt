package com.example.pioneerstask.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


class Lottie : DialogFragment() {


    fun showProgress(fragmentManager: FragmentManager, show: Boolean) {


        if (show) {
            showLocationDialoge(fragmentManager)
        } else {
            var test = fragmentManager.findFragmentByTag("lotti")
            if (test != null) {
                test = test as MyDialogFragment
                /*  if (test.isVisible()) {
                      test.dismiss()
                  }*/
                test.dismiss()
            }

        }
    }

    var dailog: MyDialogFragment? = null

    private fun showLocationDialoge(fragmentManager: FragmentManager) {
        val test = fragmentManager.findFragmentByTag("lotti")
        if (test == null) {
            val fm: FragmentManager = fragmentManager
            if (dailog == null) {
                dailog = MyDialogFragment.newInstance()
            }
            dailog!!.setCancelable(false)
            dailog!!.show(fm, "lotti")

        }

    }
}