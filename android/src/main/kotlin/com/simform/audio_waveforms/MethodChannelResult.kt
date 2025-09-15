package com.simform.audio_waveforms

import android.util.Log
import io.flutter.plugin.common.MethodChannel
import java.util.concurrent.atomic.AtomicBoolean

class MethodChannelResult(private val delegate: MethodChannel.Result) {
    private val submitted = AtomicBoolean(false)

    fun success(result: Any?): Boolean {
        if (submitted.compareAndSet(false, true)) {
            try {
                delegate.success(result)
            } catch (e: Throwable) {
                Log.d(Constants.LOG_TAG, "MethodChannel result error", e)
            }
            return true
        } else {
            Log.d(Constants.LOG_TAG, "MethodChannel result success reply submitted")
        }
        return false
    }

    fun error(
        errorCode: String,
        errorMessage: String?,
        errorDetails: Any?
    ): Boolean {
        if (submitted.compareAndSet(false, true)) {
            try {
                delegate.error(errorCode, errorMessage, errorDetails)
            } catch (e: Throwable) {
                Log.d(Constants.LOG_TAG, "MethodChannel result error", e)
            }
            return true
        } else {
            Log.d(
                Constants.LOG_TAG,
                "MethodChannel result error reply submitted: $errorCode, $errorMessage, $errorDetails"
            )
        }
        return false
    }

    fun notImplemented(): Boolean {
        if (submitted.compareAndSet(false, true)) {
            try {
                delegate.notImplemented()
            } catch (e: Throwable) {
                Log.d(Constants.LOG_TAG, "MethodChannel result error", e)
            }
            return true
        } else {
            Log.d(
                Constants.LOG_TAG,
                "MethodChannel result notImplemented reply submitted"
            )
        }
        return false
    }
}