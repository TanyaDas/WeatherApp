package com.tanya.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
public class DeleteUserFragment extends DialogFragment {
    CardView cancelCv, deleteCv;
    private DialogListener dialogListener;
    int adapterPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterPosition = getArguments().getInt("adapterPosition");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        //MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_user, null);

        cancelCv = view.findViewById(R.id.cancelCardView);
        deleteCv = view.findViewById(R.id.deletecardView);

        deleteCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialogListener != null) {
                    dialogListener.onDeleted(true, adapterPosition);
                }
                dismiss();
            }
        });
        cancelCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle refresh button click
                dismiss();
            }
        });

        builder.setView(view);
        builder.setCancelable(true);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogListener = null;
    }

    public interface DialogListener {
        void onDeleted(Boolean isDeleted, int pos);
    }
}