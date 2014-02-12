package net.niekel.weeder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Help extends DialogFragment {
MainActivity activity;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        activity = (MainActivity) getActivity();
        
        builder.setTitle(getTag());
        builder.setMessage(R.string.help);
        
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // TODO
                   }
               });
        return builder.create();
   	}
}
