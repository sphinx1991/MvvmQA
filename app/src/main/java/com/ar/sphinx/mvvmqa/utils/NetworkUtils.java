package com.ar.sphinx.mvvmqa.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sphinx on 01/09/18.
 */
public class NetworkUtils {

	private NetworkUtils() {
		// This class is not publicly instantiable
	}

	public static boolean isNetWorkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = null;
		if(cm != null) {
			ni = cm.getActiveNetworkInfo();
		}
		return ni!=null && ni.isConnectedOrConnecting();
	}
}
