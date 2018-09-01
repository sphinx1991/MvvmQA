package com.ar.sphinx.mvvmqa.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

/**
 * Created by sphinx on 01/09/18.
 */
public class BaseViewModel<N> extends ViewModel {

	public N mNavigator;
	private DataManager dataManager;
	private SchedulerProvider schedulerProvider;
	private ObservableField<Boolean> isLoading = new ObservableField<>(false);

	private CompositeDisposable compositeDisposable;

	public BaseViewModel(DataManager dataManager,SchedulerProvider schedulerProvider) {
		this.dataManager = dataManager;
		this.schedulerProvider = schedulerProvider;
		this.compositeDisposable = new CompositeDisposable();
	}

	public N getmNavigator() {
		return mNavigator;
	}

	public void setmNavigator(N mNavigator) {
		this.mNavigator = mNavigator;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public SchedulerProvider getSchedulerProvider() {
		return schedulerProvider;
	}

	public void setSchedulerProvider(SchedulerProvider schedulerProvider) {
		this.schedulerProvider = schedulerProvider;
	}

	public ObservableField<Boolean> getIsLoading() {
		return isLoading;
	}

	public void setIsLoading(boolean isLoading) {
		getIsLoading().set(isLoading);
	}

	@Override
	protected void onCleared() {
		//todo dispose
		super.onCleared();
	}
}
