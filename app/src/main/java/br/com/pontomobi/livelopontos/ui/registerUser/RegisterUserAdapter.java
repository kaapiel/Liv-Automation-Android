package br.com.pontomobi.livelopontos.ui.registerUser;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.Address;

/**
 * Created by selem.gomes on 11/09/15.
 */
public class RegisterUserAdapter extends ArrayAdapter<Address> {

    private int layoutToInflate;
    private LayoutInflater inflater;

    private Context mContext;
    private int textColor;


    public RegisterUserAdapter(Context context, List<Address> items, int layout) {
        super(context, R.layout.register_user_address_item, items);

        mContext = context;
        layoutToInflate = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RegisterUserAdapter(Context context, List<Address> items, int layout, @ColorRes int textColor) {
        super(context, R.layout.register_user_address_item, items);

        mContext = context;
        layoutToInflate = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.textColor = textColor;
    }

    public RegisterUserAdapter(Context context, List<Address> items) {
        super(context, R.layout.register_user_address_item, items);

        mContext = context;
        layoutToInflate = R.layout.register_user_address_item;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initViewHolder(View convertView, ViewHolder holder) {
        holder.address = (TextView) convertView.findViewById(R.id.register_user_address_item_address);
        holder.checkbox = (ImageView) convertView.findViewById(R.id.register_user_address_item_check);
    }

    private void configureViewHolder(Address item, final ViewHolder holder, int position) {
        holder.address.setText(item.getAddress());

        if (item.isEnable()) {
            holder.checkbox.setSelected(true);
            holder.address.setTextColor(getContext().getResources().getColor(R.color.register_user_address_enable));
        } else {
            holder.checkbox.setSelected(false);
            holder.address.setTextColor(getContext().getResources().getColor(R.color.register_user_address_disable));
        }

        if(item.isEnable() && textColor > 0){
            holder.address.setTextColor(mContext.getResources().getColor(R.color.register_user_title));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(layoutToInflate, parent, false);
            convertView.setTag(holder);

            initViewHolder(convertView, holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        configureViewHolder(getItem(position), holder, position);

        return convertView;
    }

    private class ViewHolder {
        private TextView address;
        private ImageView checkbox;
    }
}
