package br.com.pontomobi.livelopontos.ui.cart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.util.StringUtil;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView productImage;
    TextView productName;
    TextView productAmount;
    TextView productFrom;
    TextView productTo;
    TextView delete;
    Button decrease;
    Button increase;

    private CartProductListener listener;

    private int mProductsAmount;

    public ProductViewHolder(View itemView) {
        super(itemView);

        productImage = (ImageView) itemView.findViewById(R.id.cart_product_image);
        productName = (TextView) itemView.findViewById(R.id.cart_product_name);
        productAmount = (TextView) itemView.findViewById(R.id.product_amount);
        productFrom = (TextView) itemView.findViewById(R.id.cart_product_from);
        productTo = (TextView) itemView.findViewById(R.id.cart_product_to);

        delete = (TextView) itemView.findViewById(R.id.cart_product_delete);
        delete.setOnClickListener(this);

        decrease = (Button) itemView.findViewById(R.id.cart_product_decrease);
        decrease.setOnClickListener(this);

        increase = (Button) itemView.findViewById(R.id.cart_product_increase);
        increase.setOnClickListener(this);


        configureProductAmount();
    }

    private void configureProductAmount() {
        mProductsAmount = 0;
        productAmount.setText(StringUtil.formatNumber(mProductsAmount));
    }

    public void setListener(CartProductListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart_product_delete:
                deleteProduct();
                break;

            case R.id.cart_product_decrease:
                productDecrease();
                break;

            case R.id.cart_product_increase:
                productIncrease();
                break;
        }
    }

    public void deleteProduct() {
        if (listener != null)
            listener.deleteProduct((getLayoutPosition() - 1));
    }

    public void productDecrease() {
        if (mProductsAmount > 1) {
            mProductsAmount--;

            if (listener != null)
                listener.productDecrease(mProductsAmount, (getLayoutPosition() - 1));
        }

        productAmount.setText(StringUtil.formatNumber(mProductsAmount));
    }

    public void productIncrease() {
        if (mProductsAmount < 99) {
            mProductsAmount++;

            if (listener != null)
                listener.productIncrease(mProductsAmount, (getLayoutPosition() - 1));
        }

        productAmount.setText(StringUtil.formatNumber(mProductsAmount));
    }

    public void setProductsAmount(int productsAmount) {
        mProductsAmount = productsAmount;
    }

    public interface CartProductListener {
        void deleteProduct(int pos);
        void productDecrease(int amount, int pos);
        void productIncrease(int amount, int pos);
    }
}
