package com.ar.sphinx.mvvmqa.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ar.sphinx.mvvmqa.utils.CommonUtils;
import com.ar.sphinx.mvvmqa.utils.NetworkUtils;

import dagger.android.AndroidInjection;

/**
 * Created by sphinx on 01/09/18.
 */
public abstract class BaseActivity<T extends ViewDataBinding,V extends BaseViewModel> extends AppCompatActivity implements
		BaseFragment.Callback {

	private ProgressDialog progressDialog;
	private T mViewDataBinding;
	private V mViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		performAndroidInjection();
		super.onCreate(savedInstanceState);
		performDataBinding();
	}

	@Override
	public void onFragmentAttached() {

	}

	@Override
	public void onFragmentDettached(String tag) {

	}

	private void performDataBinding() {
		mViewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
		mViewModel = mViewModel == null ? getViewModel(): mViewModel;
		mViewDataBinding.setVariable(getBindingVariable(),mViewModel);
		mViewDataBinding.executePendingBindings();
	}

	private void performAndroidInjection() {
		AndroidInjection.inject(this);
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void requestPermissionsSafely(String[] permissions,int requestCode) {
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
			requestPermissions(permissions,requestCode);
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	public boolean hasPermission(String permission) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
				checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}

	public void showLoading() {
		hideLoading();
		progressDialog = CommonUtils.showLoadingDialog(this);
	}

	public void hideLoading() {
		if(progressDialog!=null && progressDialog.isShowing()) {
			progressDialog.cancel();
		}
	}

	public void openActivityOnTokenExpire(){
		//todo start loginactivity
	}

	public void hideKeyboard(){
		View view = this.getCurrentFocus();
		if(view!=null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm != null) {
				imm.hideSoftInputFromWindow(view.getWindowToken(),0);
			}
		}
	}

	public boolean isNetworkConnected(){
		return NetworkUtils.isNetWorkConnected(this);
	}

	protected T getmViewDataBinding() {
		return mViewDataBinding;
	}

	protected abstract V getViewModel();

	protected abstract int getBindingVariable();

	@LayoutRes
	protected abstract int getLayoutId();
}