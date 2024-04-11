package kz.secret_santa_jusan.core

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import io.paperdb.Paper
import kz.secret_santa_jusan.core.storage.GlobalStorage



abstract class CoreApp: Application() {


    val mFTActivityLifecycleCallbacks = FTActivityLifecycleCallbacks()
    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()

        Paper.init(applicationContext)
        registerActivityLifecycleCallbacks(mFTActivityLifecycleCallbacks)
    }

    companion object {
        private var instance: CoreApp? = null

        fun currentActivity(): ComponentActivity? {
            return instance?.mFTActivityLifecycleCallbacks?.currentActivity
        }
        fun logOut(isAuth: Boolean) {
            GlobalStorage.logOut()
            val activity = currentActivity()
            Log.d("activity","${activity != null}")
            if(activity != null) {
                val intent = activity.intent
                activity.overridePendingTransition(0, 0)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                activity.finish()
                //intent.putExtra("accessToken", GlobalStorage.getAuthToken()?.accessToken)
                activity.overridePendingTransition(0, 0)
                activity.startActivity(intent)
            }
        }

        fun kostil(){
            val activity = currentActivity()
            Log.d("activity","${activity != null}")
            if(activity != null) {
                val intent = activity.intent
                activity.overridePendingTransition(0, 0)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                activity.finish()
                //intent.putExtra("accessToken", GlobalStorage.getAuthToken()?.accessToken)
                activity.overridePendingTransition(0, 0)
                activity.startActivity(intent)
            }
        }
    }
}


class FTActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks, LifecycleObserver {

    var currentActivity: ComponentActivity? = null

    override fun onActivityPaused(activity: Activity) {
        currentActivity = activity as? ComponentActivity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity as? ComponentActivity
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity as? ComponentActivity
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity as? ComponentActivity
    }
/*
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        // Handle onResume event
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        // Handle onPause event
    }*/
}