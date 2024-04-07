package kz.secret_santa_jusan.core.base

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Context.getActivity(): ComponentActivity =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> throw IllegalArgumentException("Контекст должен быть экземпляром ComponentActivity")
    }

fun Context.getCoreBaseActivity(): CoreBaseActivity =
    when (this) {
        is ComponentActivity -> this as CoreBaseActivity
        is ContextWrapper -> this.baseContext.getActivity() as CoreBaseActivity
        else -> throw IllegalArgumentException("Контекст должен быть экземпляром ComponentActivity")
    }