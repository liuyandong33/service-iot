package build.dream.iot.models.client;

import build.dream.common.models.BasicModel;

public class ObtainMqttInfoModel extends BasicModel {
    private String deviceCode;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
