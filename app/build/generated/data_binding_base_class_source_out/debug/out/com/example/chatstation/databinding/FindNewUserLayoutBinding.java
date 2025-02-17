// Generated by view binder compiler. Do not edit!
package com.example.chatstation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chatstation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FindNewUserLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView anonymous;

  @NonNull
  public final CardView cardView2;

  @NonNull
  public final ImageView findPersonImg;

  @NonNull
  public final TextView findPersonName;

  @NonNull
  public final TextView findPersonNumber;

  private FindNewUserLayoutBinding(@NonNull ConstraintLayout rootView, @NonNull TextView anonymous,
      @NonNull CardView cardView2, @NonNull ImageView findPersonImg,
      @NonNull TextView findPersonName, @NonNull TextView findPersonNumber) {
    this.rootView = rootView;
    this.anonymous = anonymous;
    this.cardView2 = cardView2;
    this.findPersonImg = findPersonImg;
    this.findPersonName = findPersonName;
    this.findPersonNumber = findPersonNumber;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FindNewUserLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FindNewUserLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.find_new_user_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FindNewUserLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.anonymous;
      TextView anonymous = ViewBindings.findChildViewById(rootView, id);
      if (anonymous == null) {
        break missingId;
      }

      id = R.id.cardView2;
      CardView cardView2 = ViewBindings.findChildViewById(rootView, id);
      if (cardView2 == null) {
        break missingId;
      }

      id = R.id.find_person_img;
      ImageView findPersonImg = ViewBindings.findChildViewById(rootView, id);
      if (findPersonImg == null) {
        break missingId;
      }

      id = R.id.find_person_name;
      TextView findPersonName = ViewBindings.findChildViewById(rootView, id);
      if (findPersonName == null) {
        break missingId;
      }

      id = R.id.find_person_number;
      TextView findPersonNumber = ViewBindings.findChildViewById(rootView, id);
      if (findPersonNumber == null) {
        break missingId;
      }

      return new FindNewUserLayoutBinding((ConstraintLayout) rootView, anonymous, cardView2,
          findPersonImg, findPersonName, findPersonNumber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
