// Generated by view binder compiler. Do not edit!
package com.example.venturesupport.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.venturesupport.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MyprofileItemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView backButton;

  @NonNull
  public final TextView nameTextView;

  @NonNull
  public final TextView phoneNumberTextView;

  private MyprofileItemBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView backButton,
      @NonNull TextView nameTextView, @NonNull TextView phoneNumberTextView) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.nameTextView = nameTextView;
    this.phoneNumberTextView = phoneNumberTextView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MyprofileItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MyprofileItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.myprofile_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MyprofileItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_button;
      ImageView backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.name_textView;
      TextView nameTextView = ViewBindings.findChildViewById(rootView, id);
      if (nameTextView == null) {
        break missingId;
      }

      id = R.id.phone_number_textView;
      TextView phoneNumberTextView = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumberTextView == null) {
        break missingId;
      }

      return new MyprofileItemBinding((ConstraintLayout) rootView, backButton, nameTextView,
          phoneNumberTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
