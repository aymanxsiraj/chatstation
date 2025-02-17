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

public final class ActivityUserProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView bioO;

  @NonNull
  public final CardView cardView5;

  @NonNull
  public final ConstraintLayout constraintLayout2;

  @NonNull
  public final ImageView otherUserImg;

  @NonNull
  public final TextView phoneO;

  @NonNull
  public final TextView userO;

  private ActivityUserProfileBinding(@NonNull ConstraintLayout rootView, @NonNull TextView bioO,
      @NonNull CardView cardView5, @NonNull ConstraintLayout constraintLayout2,
      @NonNull ImageView otherUserImg, @NonNull TextView phoneO, @NonNull TextView userO) {
    this.rootView = rootView;
    this.bioO = bioO;
    this.cardView5 = cardView5;
    this.constraintLayout2 = constraintLayout2;
    this.otherUserImg = otherUserImg;
    this.phoneO = phoneO;
    this.userO = userO;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_user_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUserProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bioO;
      TextView bioO = ViewBindings.findChildViewById(rootView, id);
      if (bioO == null) {
        break missingId;
      }

      id = R.id.cardView5;
      CardView cardView5 = ViewBindings.findChildViewById(rootView, id);
      if (cardView5 == null) {
        break missingId;
      }

      id = R.id.constraintLayout2;
      ConstraintLayout constraintLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout2 == null) {
        break missingId;
      }

      id = R.id.other_user_img;
      ImageView otherUserImg = ViewBindings.findChildViewById(rootView, id);
      if (otherUserImg == null) {
        break missingId;
      }

      id = R.id.phoneO;
      TextView phoneO = ViewBindings.findChildViewById(rootView, id);
      if (phoneO == null) {
        break missingId;
      }

      id = R.id.userO;
      TextView userO = ViewBindings.findChildViewById(rootView, id);
      if (userO == null) {
        break missingId;
      }

      return new ActivityUserProfileBinding((ConstraintLayout) rootView, bioO, cardView5,
          constraintLayout2, otherUserImg, phoneO, userO);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
