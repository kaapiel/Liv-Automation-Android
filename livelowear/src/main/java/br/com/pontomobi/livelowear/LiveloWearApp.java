package br.com.pontomobi.livelowear;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.service.model.WearRescueCode;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class LiveloWearApp extends Application {
    private static LiveloWearApp instance;
    private Bus bus;
    private WearPoints wearPoints;
    private WearRescueCode wearRescueCode;


    @Override
    public void onCreate() {
        super.onCreate();

        wearPoints = new WearPoints();
        instance = this;
        bus = new Bus(ThreadEnforcer.ANY);
        wearRescueCode = null;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static LiveloWearApp getInstance() {
        return instance;
    }

    public Bus getBus() {
        return bus;
    }

    public WearPoints getWearPoints() {
        return wearPoints;
    }

    public void setWearPoints(WearPoints wearPoints) {
        this.wearPoints = wearPoints;
    }

    public WearRescueCode getWearRescueCode() {
        return wearRescueCode;
    }

    public void setWearRescueCode(WearRescueCode wearRescueCode) {
        this.wearRescueCode = wearRescueCode;
    }
}
