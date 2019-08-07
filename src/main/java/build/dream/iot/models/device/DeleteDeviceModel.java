package build.dream.iot.models.device;

import build.dream.common.models.IotBasicModel;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public class DeleteDeviceModel extends IotBasicModel {
    @NotNull
    private BigInteger deviceId;

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }
}
