package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rey.material.widget.EditText;

import br.com.aguido.livautomation.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class PasswordComponent extends FrameLayout implements View.OnClickListener {

    private EditText password;
    private ImageView icon;
    private Context context;
    private boolean passShow = true;

    public PasswordComponent(Context context) {
        super(context);
    }

    public PasswordComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ComponentPassword);
        String hint = styledAttrs.getString(R.styleable.ComponentPassword_hint_pass);

        styledAttrs.recycle();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_password, this, true);

        password = (EditText) findViewById(R.id.component_password_pass);
        icon = (ImageView) findViewById(R.id.component_password_icon);

        Typeface museoSans300 = Typeface.createFromAsset(context.getAssets(), "fonts/MuseoSans_300.otf");
        password.setTypeface(museoSans300);

        icon.setOnClickListener(this);

        if(!TextUtils.isEmpty(hint)) {
            password.setHint(hint);
        }
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.component_password_icon:
                if(passShow) {
                    password.setTransformationMethod(null);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
                password.setSelection(password.getText().length());
                icon.setSelected(passShow);
                passShow = !passShow;
                break;
        }
    }
}
