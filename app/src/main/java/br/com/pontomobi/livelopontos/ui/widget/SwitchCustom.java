package br.com.pontomobi.livelopontos.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.Switch;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class SwitchCustom extends RelativeLayout {

    private TextView switchCustomText;
    private Switch switchCustomSwitch;
    private Context context;

    public SwitchCustom(Context context) {
        super(context);
    }

    public SwitchCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.SwitchCustom);
        String textEdit = styledAttrs.getString(R.styleable.SwitchCustom_switch_custom_text);

        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.switch_custom, this, true);

        switchCustomText = (TextView) findViewById(R.id.switch_custom_text);
        switchCustomSwitch = (Switch) findViewById(R.id.switch_custom_switch);

        if (textEdit != null) {
            switchCustomText.setText(textEdit);
        }
    }

    public TextView getSwitchCustomText() {
        return switchCustomText;
    }

    public void setSwitchCustomText(TextView switchCustomText) {
        this.switchCustomText = switchCustomText;
    }

    public Switch getSwitchCustomSwitch() {
        return switchCustomSwitch;
    }

    public void setSwitchCustomSwitch(Switch switchCustomSwitch) {
        this.switchCustomSwitch = switchCustomSwitch;
    }
}
