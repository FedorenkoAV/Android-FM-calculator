package ru.fmproject.android.calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * FM_Calculator
 * Создано Федоренко Александром 15.01.2019.
 */
public class MyDialogFragment extends DialogFragment {

    final String TAG = "MyDialogFragment";

    TextView mTextView;
    String msgString;

    public interface NoticeDialogListener {
        void onDialogNegativeClick(MyDialogFragment myDialogFragment);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.d(TAG, "onAttach() запущен.");
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        L.d(TAG, "onCreateDialog() запущен.");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog, null);
        mTextView = v.findViewById(R.id.tvAlertDialogProtocol);
        mTextView.setText(msgString);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
// Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(MyDialogFragment.this);

                    }
                });
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        L.d(TAG, "onDismiss() запущен.");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        L.d(TAG, "onCancel() запущен.");
    }

    public void setText (String msg) {
        msgString = msg;
    }


}