package build.dream.iot.services;

import build.dream.common.api.ApiRest;
import build.dream.common.domains.iot.Device;
import build.dream.common.utils.DatabaseHelper;
import build.dream.common.utils.SearchModel;
import build.dream.common.utils.ValidateUtils;
import build.dream.iot.models.client.ObtainDeviceInfoModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ClientService {
    @Transactional(readOnly = true)
    public ApiRest obtainDeviceInfo(ObtainDeviceInfoModel obtainDeviceInfoModel) {
        String deviceCode = obtainDeviceInfoModel.getDeviceCode();

        SearchModel searchModel = SearchModel.builder()
                .autoSetDeletedFalse()
                .equal(Device.ColumnName.CODE, deviceCode)
                .build();
        Device device = DatabaseHelper.find(Device.class, searchModel);
        ValidateUtils.notNull(device, "设备不存在！");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("deviceCode", device.getCode());
        data.put("clientId", UUID.randomUUID().toString());
        data.put("clientSecret", UUID.randomUUID().toString());
        return ApiRest.builder().data(data).message("获取设备信息成功").successful(true).build();
    }
}
