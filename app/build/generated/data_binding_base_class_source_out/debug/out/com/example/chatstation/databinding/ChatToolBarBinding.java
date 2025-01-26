// Generated by view binder compiler. Do not edit!
package com.example.chatstation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chatstation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ChatToolBarBinding implements ViewBinding {
  @NonNull
  private final Toolbar rootView;

  @NonNull
  public final CardView cardView3;

  @NonNull
  public final TextView chatName;

  @NonNull
  public final ImageView chatProfile;

  @NonNull
  public final TextView status;

  private ChatToolBarBinding(@NonNull Toolbar rootView, @NonNull CardView cardView3,
      @NonNull TextView chatName, @NonNull ImageView chatProfile, @NonNull TextView status) {
    this.rootView = rootView;
    this.cardView3 = cardView3;
    this.chatName = chatName;
    this.chatProfile = chatProfile;
    this.status = status;
  }

  @Override
  @NonNull
  public Toolbar getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatToolBarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatToolBarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat_tool_bar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatToolBarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView3;
      CardView cardView3 = ViewBindings.findChildViewById(rootView, id);
      if (cardView3 == null) {
        break missingId;
      }

      id = R.id.chat_name;
      TextView chatName = ViewBindings.findChildViewById(rootView, id);
      if (chatName == null) {
        break missingId;
      }

      id = R.id.chat_profile;
      ImageView chatProfile = ViewBindings.findChildViewById(rootView, id);
      if (chatProfile == null) {
        break missingId;
      }

      id = R.id.status;
      TextView status = ViewBindings.findChildViewById(rootView, id);
      if (status == null) {
        break missingId;
      }

      return new ChatToolBarBinding((Toolbar) rootView, cardView3, chatName, chatProfile, status);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
