package br.com.pontomobi.livelopontos.ui.qrcode;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import butterknife.Bind;
import butterknife.ButterKnife;

public class QRCodeFragment extends LiveloPontoFragment implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String PRODUCT_PATTERN = "livelo://product/";

    @Bind(R.id.qrdecoderview)
    QRCodeReaderView mQRCodeReaderView;

    private boolean showDialog = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_qr_code, container, false);
        ButterKnife.bind(this, view);
        mQRCodeReaderView.setOnQRCodeReadListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuSelected(R.id.menu_qr_code);
        mQRCodeReaderView.getCameraManager().startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        mQRCodeReaderView.getCameraManager().stopPreview();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    private String getIdProduct(String uri) {
        return uri.replace(PRODUCT_PATTERN, "");
    }

    private void openPage(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void openUrl(Alert alert, final String urlPage) {
        alert.setDescription(getString(R.string.qr_code_access_external));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            showDialog = true;

                            String url = urlPage;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                        @Override
                        public void onNegativeClick() {
                            showDialog = true;
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            showDialog = true;
                        }
                    });
        }
    }
}
