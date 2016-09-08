package br.com.pontomobi.livelopontos.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by vilmar.filho on 5/11/16.
 */
public class DialogLoading {

    private Context context;
    private Dialog dialog;

    public DialogLoading(Context c){
        context = c;
    }

    public void show(){
        dialog = new Dialog(context, R.style.LiveloPontosTheme_Dialog_Loading);

        RelativeLayout contentView = (RelativeLayout) ((Activity) context).getLayoutInflater().inflate(R.layout.loading, null) ;

        dialog.setContentView(contentView);

        dialog.setCancelable(false);

        dialog.findViewById(R.id.loading_content).setVisibility(View.VISIBLE);

        if (!((Activity) context).isFinishing())
            dialog.show();
    }

    public void hide(){
        if (dialog != null)
            dialog.dismiss();
    }

}
