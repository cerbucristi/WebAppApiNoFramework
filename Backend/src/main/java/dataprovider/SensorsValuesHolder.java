package dataprovider;

import models.FlowerDynamicSpecs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SensorsValuesHolder {
    private Map<Integer, FlowerDynamicSpecs> sensorValuesHolder;

    private static volatile SensorsValuesHolder instance;

    private SensorsValuesHolder (){
        sensorValuesHolder = new ConcurrentHashMap<>();
    }

    public static SensorsValuesHolder getInstance() {
        if (instance == null) {
            synchronized (SensorsValuesHolder.class) {
                if (instance == null) {
                    instance = new SensorsValuesHolder();
                }
            }
        }
        return instance;
    }

    public Map<Integer, FlowerDynamicSpecs> getSensorValuesHolder() {
        return sensorValuesHolder;
    }


}
