package com.redone.pplr.common;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.redone.pplr.util.LogUtils;

public class RestFields {

	private LinearLayout layoutForms;
	public RestFields(LinearLayout layout) {
		layoutForms = layout;
	}
	public RestFields() {
	}
	private List<View> getAllChildrenBFS(View v) {
		List<View> visited = new ArrayList<View>();
		List<View> unvisited = new ArrayList<View>();
		unvisited.add(v);

		while (!unvisited.isEmpty()) {
			View child = unvisited.remove(0);
			visited.add(child);
			if (!(child instanceof ViewGroup))
				continue;
			ViewGroup group = (ViewGroup) child;
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++)
				unvisited.add(group.getChildAt(i));
		}
		return visited;

	}

	protected void validateChild() {
		List<View> viewList = getAllChildrenBFS(layoutForms);
		for (View view : viewList) {
			if (view instanceof EditText) {
				restTextView(((EditText) view));
			}
			if (view instanceof Spinner) {
				restSpinner(((Spinner) view));
			}
		}
	}

	private void restSpinner(Spinner spinner) {
		spinner.setSelection(0);
	}

	private void restTextView(EditText editText) {
		editText.setText("");
	}
	protected void validateChild2() {
		List<View> visited = new ArrayList<View>();
		List<View> unvisited = new ArrayList<View>();
		unvisited.add(layoutForms);

		while (!unvisited.isEmpty()) {
			View child = unvisited.remove(0);
			visited.add(child);
			
			if (!(child instanceof ViewGroup))
				continue;
			
			ViewGroup group = (ViewGroup) child;
			final int childCount = group.getChildCount();
			
			for (int i = 0; i < childCount; i++)
				unvisited.add(group.getChildAt(i));
			
			if (child instanceof EditText) {
				disableEditText(((EditText) child));
			}
			if (child instanceof Spinner) {
				disableSpinner(((Spinner) child));
			}
			if (child instanceof CheckBox) {
				disableCheckBox(((CheckBox) child));
			}
			if (child instanceof Button) {
				disableButton(((Button) child));
			}
		}
	}
	private void disableButton(Button button) {
		setDisabled((View)button);
		button.setBackgroundColor(Color.parseColor("#ff33b5e5"));
	}

	public void disableCheckBox(CheckBox checkBox) {
		setDisabled((View)checkBox);
		//checkBox.setBackgroundColor(Color.parseColor("#ff33b5e5"));
	}
	public void enableCheckBox(CheckBox checkBox) {
		setEnabled((View)checkBox);	
	}
	private boolean checkEditTextIsEmpty(EditText editText) {
		boolean isNotEmpty = true;
		EditText et = editText;
		if (et.getText().toString().equals("")) {
			
			et.setError("Cannot be empty");
			isNotEmpty = false;
		} else {
			et.setError(null);
		}

		return isNotEmpty;
	}
	private boolean checkSpinnerIsEmpty(Spinner spinner) {
		boolean isNotEmpty = true;
		
		if (spinner.getSelectedItemPosition()==0) {
			spinner.setFocusable(true);
			((TextView) spinner.getChildAt(0)).setTextColor(Color.parseColor("#ff0000"));
			((TextView) spinner.getChildAt(0)).setText("-Can not Be Empty-Please Select");
			setFocusable((View)spinner);
		}
		return isNotEmpty;
	}
	public void disableSpinner(Spinner spinner) {
		setDisabled((View)spinner);
		if(spinner.getChildAt(0) != null)
			((TextView)spinner.getChildAt(0)).setTextColor(Color.parseColor("#ff33b5e5"));

	}
	public void setFocusable(View view){
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}
	
	private void disableEditText(EditText editText) {
		setDisabled((View)editText);
		editText.setTextColor(Color.parseColor("#ff33b5e5"));		
	}

	public void restAll() {
		LogUtils.LOGD("setToUnedView", "setToUneditableView()|");
		validateChild();
	}
	public void disableAll(){
		validateChild2();
	}
	private void setDisabled(View view){
		view.setEnabled(false);
		view.setFocusable(false);
		view.setClickable(false);
	}
	private void setEnabled(View view){
		view.setEnabled(true);
		view.setFocusable(true);
		view.setClickable(true);
	}
}
