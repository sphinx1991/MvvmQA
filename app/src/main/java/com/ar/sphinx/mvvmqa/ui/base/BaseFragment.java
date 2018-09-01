package com.ar.sphinx.mvvmqa.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by sphinx on 01/09/18.
 */
public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

	private BaseActivity mActivity;
	private T mViewDataBinding;
	private V mViewModel;
	private View rootView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		performDependencyInjection();
		super.onCreate(savedInstanceState);
		mViewModel = getViewModel();
		setHasOptionsMenu(false);
	}

	protected abstract V getViewModel();

	private void performDependencyInjection() {
		AndroidSupportInjection.inject(this);
	}

	public BaseActivity getBaseActivity() {
		return mActivity;
	}

	public T getViewDataBinding() {
		return mViewDataBinding;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(),getLayoutId(),container,false);
		rootView = mViewDataBinding.getRoot();
		return rootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewDataBinding.setVariable(getBindingVariable(),mViewModel);
		mViewDataBinding.executePendingBindings();
	}

	public abstract int getBindingVariable();
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if(context instanceof BaseActivity) {
			BaseActivity baseActivity = (BaseActivity) context;
			this.mActivity = baseActivity;
			baseActivity.onFragmentAttached();
		}
	}

	public boolean isNetworkConnected() {
		return mActivity!=null && mActivity.isNetworkConnected();
	}

	public void hideKeyboard() {
		if (mActivity != null) {
			mActivity.hideKeyboard();
		}
	}

	public void openActivityOnTokenExpire() {
		if (mActivity != null) {
			mActivity.openActivityOnTokenExpire();
		}
	}

	@Override
	public void onDetach() {
		mActivity = null;
		super.onDetach();
	}

	public interface Callback {

		void onFragmentAttached();

		void onFragmentDettached(String tag);
	}

	public abstract int getLayoutId();
}
