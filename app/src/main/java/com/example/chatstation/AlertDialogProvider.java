package com.example.chatstation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.auth.FirebaseAuth;



public class AlertDialogProvider extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        stateDialog stateDialog = new stateDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("log out")
                .setMessage("are you sure want to log out !")
                .setPositiveButton("log out", (dialog, which) -> {

                    try {
                                stateDialog.updateUserStatus();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                requireActivity().finish();

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("cancel", (dialog, which) -> {

                });
        return builder.create();

    }



}
