package br.com.aguido.livautomation.ui.webview;

import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationActivity;
import br.com.aguido.livautomation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 11/11/15.
 */
public class WebViewActivity extends LivAutomationActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.assinatura_container)
    RelativeLayout assinaturaContainer;
    @BindView(R.id.loading_content)
    RelativeLayout loadingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        loadingContent.setVisibility(View.VISIBLE);
        configureActionBar();
        showWebsite(Constants.URL_FORGOT_PASSWORD);
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(getString(R.string.webview_forgot_password_toolbar_title));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showWebsite(String url) {
        final WebView webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingContent.setVisibility(View.GONE);
                view.getSettings();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });

        webView.requestFocus(View.FOCUS_DOWN);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setHorizontalScrollBarEnabled(false);
        webView.loadUrl(url);

        if (assinaturaContainer.getChildCount() > 0)
            assinaturaContainer.removeAllViews();

        assinaturaContainer.addView(webView);
    }
}
