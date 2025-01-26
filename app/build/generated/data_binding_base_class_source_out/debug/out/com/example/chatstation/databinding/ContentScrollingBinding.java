// Generated by view binder compiler. Do not edit!
package com.example.chatstation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chatstation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.Override;

public final class ContentScrollingBinding implements ViewBinding {
  @NonNull
  private final View rootView;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final TextInputEditText bio;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final MaterialButton goToHome;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final MaterialButton saveInfo;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final TextInputEditText userName;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final TextInputEditText userPhoneNumber;

  private ContentScrollingBinding(@NonNull View rootView, @Nullable TextInputEditText bio,
      @Nullable MaterialButton goToHome, @Nullable MaterialButton saveInfo,
      @Nullable TextInputEditText userName, @Nullable TextInputEditText userPhoneNumber) {
    this.rootView = rootView;
    this.bio = bio;
    this.goToHome = goToHome;
    this.saveInfo = saveInfo;
    this.userName = userName;
    this.userPhoneNumber = userPhoneNumber;
  }

  @Override
  @NonNull
  public View getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentScrollingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentScrollingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_scrolling, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentScrollingBinding bind(@NonNull View rootView) {
    TextInputEditText bio = ViewBindings.findChildViewById(rootView, R.id.bio);

    MaterialButton goToHome = ViewBindings.findChildViewById(rootView, R.id.go_to_home);

    MaterialButton saveInfo = ViewBindings.findChildViewById(rootView, R.id.save_info);

    TextInputEditText userName = ViewBindings.findChildViewById(rootView, R.id.user_name);

    TextInputEditText userPhoneNumber = ViewBindings.findChildViewById(rootView, R.id.user_phone_number);

    return new ContentScrollingBinding(rootView, bio, goToHome, saveInfo, userName,
        userPhoneNumber);
  }
}
