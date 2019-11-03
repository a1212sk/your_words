package alexander.skornyakov.yourwords.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.UnknownHostException

class Utils {
    companion object{
        fun hideKeyboard(activity: FragmentActivity) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // Check if no view has focus
            val currentFocusedView = activity.currentFocus
            currentFocusedView?.let {
                inputMethodManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

         fun isNetworkAvailable(context: Context): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

         fun isInternetAvailable(context: Context): Boolean = runBlocking {
            try {
                if(isNetworkAvailable(context)) {
                    val address = withContext(Dispatchers.IO) { InetAddress.getByName("www.google.com") }
                    !address.equals("")
                }
                else{
                    false
                }
            } catch (e: UnknownHostException) {
                false
            }
        }
    }

}