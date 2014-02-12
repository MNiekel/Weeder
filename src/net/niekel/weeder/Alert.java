package net.niekel.weeder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Alert extends DialogFragment {
	GameActivity activity;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        activity = (GameActivity) getActivity();
        String message;
        if (args.getBoolean(activity.KEY_FINISHED)) {
        	int score = args.getInt(activity.KEY_SCORE);
        	int hiscore = args.getInt(activity.KEY_HISCORE);
        	message = activity.getString(R.string.message_gameover_1) +score+ activity.getString(R.string.message_gameover_2);
        	if (score == hiscore) {
        		message = message + activity.getString(R.string.message_gameover_4);
        	} else {
        		message = message + activity.getString(R.string.message_gameover_5) +hiscore+ activity.getString(R.string.message_gameover_2);
        	}
        	message = message + activity.getString(R.string.message_gameover_3);
        } else {
        	message = activity.getString(R.string.message_quit);
        }
        
        builder.setTitle(getTag());
        builder.setMessage(message);
        
        builder.setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       activity.restartGame();
                   }
               })
               .setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   activity.finish();
                   }
               });
        return builder.create();
   	}
}
