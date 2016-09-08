package br.com.pontomobi.livelopontos.ui.contactUs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.NetworkHelper;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 5/10/16.
 */
public class ContactUsFragment extends LiveloPontoFragment {

    private static final String HTML_CONTACT_US = "file:///android_asset/contact_us.html";

    @Bind(R.id.wv_content) WebView wvContent;

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebview();

        if (!NetworkHelper.isOnline(getActivity())) {
            showErrorConnection();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuSelected(R.id.menu_contact_us);
    }

    private void setupWebview() {
        WebSettings webSettings = wvContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setAllowFileAccess(true);
        //webSettings.setUserAgentString("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>");

        wvContent.setWebViewClient(getWebViewClient());
        wvContent.setWebChromeClient(new WebChromeClient());

        wvContent.loadUrl(HTML_CONTACT_US);
   }

    private WebViewClient getWebViewClient() {
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                showDialogURL(url);
                return true;
            }
        };

        return webViewClient;
    }

    private void showErrorConnection() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), LiveloPontosApp.getInstance().getNoConnection(), true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            if (NetworkHelper.isOnline(getActivity())) {
                                wvContent.loadUrl(HTML_CONTACT_US);
                            } else {
                                showErrorConnection();
                            }
                        }

                        @Override
                        public void onNegativeClick() { }

                        @Override
                        public void onBackPressedInDialog() {
                            getActivity().onBackPressed();
                        }
                    });
        }
    }

    private void showDialogURL(final String url) {
        new DialogCustomAlert().showCustomDialog(getActivity(), LiveloPontosApp.getInstance().getOpenUrl(), true,
                new DialogCustomAlert.AlertDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }

                    @Override
                    public void onNegativeClick() { }

                    @Override
                    public void onBackPressedInDialog() { }
                });
    }
}
