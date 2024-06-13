// Generated by view binder compiler. Do not edit!
package com.example.venturesupport.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.venturesupport.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class IncomechartBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView IncomeView;

  @NonNull
  public final TextView amountTextView;

  @NonNull
  public final CalendarView calendarView;

  @NonNull
  public final TextView countTextView;

  @NonNull
  public final TextView dateTextView;

  private IncomechartBinding(@NonNull RelativeLayout rootView, @NonNull TextView IncomeView,
      @NonNull TextView amountTextView, @NonNull CalendarView calendarView,
      @NonNull TextView countTextView, @NonNull TextView dateTextView) {
    this.rootView = rootView;
    this.IncomeView = IncomeView;
    this.amountTextView = amountTextView;
    this.calendarView = calendarView;
    this.countTextView = countTextView;
    this.dateTextView = dateTextView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static IncomechartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static IncomechartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.incomechart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static IncomechartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Income_View;
      TextView IncomeView = ViewBindings.findChildViewById(rootView, id);
      if (IncomeView == null) {
        break missingId;
      }

      id = R.id.amountTextView;
      TextView amountTextView = ViewBindings.findChildViewById(rootView, id);
      if (amountTextView == null) {
        break missingId;
      }

      id = R.id.calendarView;
      CalendarView calendarView = ViewBindings.findChildViewById(rootView, id);
      if (calendarView == null) {
        break missingId;
      }

      id = R.id.countTextView;
      TextView countTextView = ViewBindings.findChildViewById(rootView, id);
      if (countTextView == null) {
        break missingId;
      }

      id = R.id.dateTextView;
      TextView dateTextView = ViewBindings.findChildViewById(rootView, id);
      if (dateTextView == null) {
        break missingId;
      }

      return new IncomechartBinding((RelativeLayout) rootView, IncomeView, amountTextView,
          calendarView, countTextView, dateTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
