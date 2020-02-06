package br.com.aguido.livautomation.ui.widget;

public enum EnumBanner {

    HOME("bannerHome"),
    PLP("bannerPLP"),
    PDP("bannerPDP"),
    CART("bannerCart"),
    SEARCH("bannerSearch");

    private String name;

    EnumBanner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
