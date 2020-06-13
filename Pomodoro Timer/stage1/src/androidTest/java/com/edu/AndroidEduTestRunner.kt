package com.edu

import androidx.test.runner.AndroidJUnitRunner

class AndroidEduTestRunner : AndroidJUnitRunner() {

    override fun sendStatus(resultCode: Int, results: android.os.Bundle) {
        if (resultCode < 0) {
            val stack = results.getString("stack")
            val testTitle = results.get("test")
            if (stack != null) {
                val errorMessage = stack.substringAfter(":").trim().substringBefore("\n")
                results.putString("stack", "#educational_plugin FAILED + Wrong answer in test $testTitle: $errorMessage")
            }
        }
        super.sendStatus(resultCode, results)
    }
}
