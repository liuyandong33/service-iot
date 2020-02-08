package build.dream.iot.models.client;

import build.dream.common.models.BasicModel;

import javax.validation.constraints.NotNull;

public class UploadDataModel extends BasicModel {
    @NotNull
    private String id;

    @NotNull
    private String deviceCode;

    @NotNull
    private Double temperature;

    @NotNull
    private Double humidity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
