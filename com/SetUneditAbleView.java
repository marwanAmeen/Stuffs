package com.redone.pplr.common;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.redone.pplr.util.LogUtils;
/**
 * 
 * @author marwan
 * 
 * This class was create to solve a problem of making the controllers not Editable 
 *  such as EditText,Spinner, And CheckBox,However theses controllers will be uneditable only and only if the 
 *  create line spinner is set to subline  and result was found other wise won't work.
 *
 */
public class SetUneditAbleView {
	private LinearLayout layoutForms;
	public SetUneditAbleView(LinearLayout layout) {
		layoutForms = layout;
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
				uneditAbleTextView(((EditText) view));
			}
			if (view instanceof Spinner) {
				uneditAbleSpinner(((Spinner) view));
			}
		}
	}

	private void uneditAbleSpinner(Spinner spinner) {
		spinner.setEnabled(false);
		spinner.setClickable(false);
		spinner.setFocusable(false);
	}

	private void uneditAbleTextView(EditText editText) {
		editText.setEnabled(false);
		editText.setClickable(false);
		editText.setFocusable(false);
	}

	public void setToUneditableView() {
		LogUtils.LOGD("setToUnedView", "setToUneditableView()|");
		validateChild();
	}
}
