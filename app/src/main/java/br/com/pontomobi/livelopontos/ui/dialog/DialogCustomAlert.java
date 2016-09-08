package br.com.pontomobi.livelopontos.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.util.Util;

/**
 * Created by selemafonso on 10/11/15.
 */
public class DialogCustomAlert {
    ImageView customDialogAlertsImage;
    TextView customDialogAlertsTitle;
    TextView customDialogAlertsDescription;
    TextView customDialogAlertsPositiveButton;
    TextView customDialogAlertsNegativeButton;
    EditText customDialogAlertsPassword;

    public void showCustomDialog(final Context context, Alert alert, final boolean isCancelable,
                                 final AlertDialogClickListener alertDialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.LiveloPontosTheme_CustomDialog);

        dialog.setContentView(R.layout.custom_dialog_alerts);
        if (!isCancelable) {
            dialog.setCancelable(false);
        }

        customDialogAlertsImage = (ImageView) dialog.findViewById(R.id.custom_dialog_alerts_image);
        customDialogAlertsTitle = (TextView) dialog.findViewById(R.id.custom_dialog_alerts_title);
        customDialogAlertsDescription = (TextView) dialog.findViewById(R.id.custom_dialog_alerts_description);
        customDialogAlertsPositiveButton = (TextView) dialog.findViewById(R.id.custom_dialog_alerts_positive_button);
        customDialogAlertsNegativeButton = (TextView) dialog.findViewById(R.id.custom_dialog_alerts_negative_button);
        customDialogAlertsPassword = (EditText) dialog.findViewById(R.id.custom_dialog_alerts_password);
        showCustomDialog(context, alert);
        setTypefaceInEditText(context);

        customDialogAlertsPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                alertDialogClickListener.onPositiveClick();
            }
        });

        customDialogAlertsNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                alertDialogClickListener.onNegativeClick();
            }
        });

        dialog.show();

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!isCancelable) {
                        return false;
                    }
                    dialog.dismiss();
                    alertDialogClickListener.onBackPressedInDialog();
                }
                return false;
            }
        });
    }

    private void showCustomDialog(Context context, Alert alert) {

        if(!TextUtils.isEmpty(alert.getImage())) {
            customDialogAlertsImage.setImageDrawable(Util.getDrawableByName(context, alert.getImage()));
        }
        customDialogAlertsTitle.setText(alert.getTitle());
        customDialogAlertsDescription.setText(alert.getDescription());
        customDialogAlertsPositiveButton.setText(alert.getPositiveButton());
        customDialogAlertsNegativeButton.setText(alert.getNegativeButton());

        if (TextUtils.isEmpty(alert.getNegativeButton())) {
            customDialogAlertsNegativeButton.setVisibility(View.GONE);
        }

        if(alert.isGetPassword()) {
            customDialogAlertsPassword.setVisibility(View.VISIBLE);
        }

    }

    public String getPassword() {
        return customDialogAlertsPassword.getText().toString();
    }

    private void setTypefaceInEditText(Context context) {
        Typeface museoSans = Typeface.createFromAsset(context.getAssets(), "fonts/MuseoSans_700.otf");
        customDialogAlertsPassword.setTypeface(museoSans);
    }

    public interface AlertDialogClickListener {
        void onPositiveClick();

        void onNegativeClick();

        void onBackPressedInDialog();
    }
}
