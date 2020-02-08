package build.dream.iot.models.client;

import build.dream.common.models.BasicModel;

import javax.validation.constraints.NotNull;

public class ObtainDataModel extends BasicModel {
    @NotNull
    private String deviceCode;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
