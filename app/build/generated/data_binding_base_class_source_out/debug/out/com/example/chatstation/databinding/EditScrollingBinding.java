// Generated by view binder compiler. Do not edit!
package com.example.chatstation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chatstation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EditScrollingBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final TextView userBioEdit;

  @NonNull
  public final TextView userNameEdit;

  @NonNull
  public final TextView userPhoneNumberEdit;

  private EditScrollingBinding(@NonNull NestedScrollView rootView, @NonNull TextView userBioEdit,
      @NonNull TextView userNameEdit, @NonNull TextView userPhoneNumberEdit) {
    this.rootView = rootView;
    this.userBioEdit = userBioEdit;
    this.userNameEdit = userNameEdit;
    this.userPhoneNumberEdit = userPhoneNumberEdit;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static EditScrollingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EditScrollingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.edit_scrolling, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EditScrollingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.user_bio_edit;
      TextView userBioEdit = ViewBindings.findChildViewById(rootView, id);
      if (userBioEdit == null) {
        break missingId;
      }

      id = R.id.user_name_edit;
      TextView userNameEdit = ViewBindings.findChildViewById(rootView, id);
      if (userNameEdit == null) {
        break missingId;
      }

      id = R.id.user_phone_number_edit;
      TextView userPhoneNumberEdit = ViewBindings.findChildViewById(rootView, id);
      if (userPhoneNumberEdit == null) {
        break missingId;
      }

      return new EditScrollingBinding((NestedScrollView) rootView, userBioEdit, userNameEdit,
          userPhoneNumberEdit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
