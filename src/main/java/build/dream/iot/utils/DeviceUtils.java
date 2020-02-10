package build.dream.iot.utils;

import build.dream.common.domains.iot.Device;
import build.dream.common.utils.DatabaseHelper;
import build.dream.common.utils.SearchModel;

public class DeviceUtils {
    public static Device findDeviceByClientId(String clientId) {
        SearchModel searchModel = SearchModel.builder()
                .autoSetDeletedFalse()
                .equal(Device.ColumnName.CLIENT_ID, clientId)
                .build();
        return DatabaseHelper.find(Device.class, searchModel);
    }
}
