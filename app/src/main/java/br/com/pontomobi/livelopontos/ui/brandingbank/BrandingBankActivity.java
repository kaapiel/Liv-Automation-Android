package br.com.pontomobi.livelopontos.ui.brandingbank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.model.PartnerPartyEnrollment;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.tutorial.TutorialActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 31/08/15.
 */
public class BrandingBankActivity extends LiveloPontosActivity {

    @Bind(R.id.bank_img1)
    ImageView bankImg1;
    @Bind(R.id.bank_img2)
    ImageView bankImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(LiveloPontosApp.getInstance().getBrandingBank() == null) {
            openTutorialOrHome();
            return;
        }

        setContentView(R.layout.activity_branding_bank);
        ButterKnife.bind(this);
        configScreen();
        openNextScreen();
    }

    private void openNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openTutorialOrHome();
            }
        }, 2000);
    }

    private void configScreen() {
        int code = LiveloPontosApp.getInstance().getBrandingBank().getBankToShow();

        switch (code) {
            case PartnerPartyEnrollment.PARTNER_BANCO_BRADESCO:
                bankImg1.setImageDrawable(getResources().getDrawable(R.drawable.bradesco_fidelidade));
                bankImg2.setImageDrawable(getResources().getDrawable(R.drawable.bradesco_cartoes));
                break;

            case PartnerPartyEnrollment.PARTNER_BANCO_BRASIL:
                bankImg1.setImageDrawable(getResources().getDrawable(R.drawable.bb_ponto));
                bankImg2.setImageDrawable(getResources().getDrawable(R.drawable.bb_parceria));
                break;

            default:
                openTutorialOrHome();
                break;
        }
    }

    private void openTutorialOrHome() {
        Intent intent;

        if (showTutorial()) {
            intent = new Intent(this, TutorialActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

    private boolean showTutorial() {
        boolean showTutorial = SharedPreferencesHelper.read(this, Constants.SharedPrefsKeys.SHOW_TUTORIAL, "showTutorial", true);

        if (showTutorial) {
            return (true);
        }

        return (false);
    }

}
