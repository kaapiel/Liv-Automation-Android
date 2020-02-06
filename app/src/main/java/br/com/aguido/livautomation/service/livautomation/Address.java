package br.com.aguido.livautomation.service.livautomation;

/**
 * Created by selem.gomes on 11/09/15.
 */
public class Address {
    private String address;
    private boolean enable;

    public Address(String address, boolean enable) {
        this.address = address;
        this.enable = enable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
