package build.dream.iot.services;

import build.dream.common.api.ApiRest;
import build.dream.common.domains.iot.Device;
import build.dream.common.domains.saas.MqttConfig;
import build.dream.common.models.mqtt.ApplyTokenModel;
import build.dream.common.mqtt.MqttInfo;
import build.dream.common.utils.*;
import build.dream.iot.models.client.ObtainDataModel;
import build.dream.iot.models.client.ObtainDeviceInfoModel;
import build.dream.iot.models.client.ObtainMqttInfoModel;
import build.dream.iot.models.client.UploadDataModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ClientService {
    /**
     * @param obtainDeviceInfoModel
     * @return
     */
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

    /**
     * 获取MqttInfo
     *
     * @param obtainMqttInfoModel
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiRest obtainMqttInfo(ObtainMqttInfoModel obtainMqttInfoModel) {
        String deviceCode = obtainMqttInfoModel.getDeviceCode();

        SearchModel searchModel = SearchModel.builder()
                .autoSetDeletedFalse()
                .equal(Device.ColumnName.CODE, deviceCode)
                .build();
        Device device = DatabaseHelper.find(Device.class, searchModel);
        ValidateUtils.notNull(device, "设备不存在！");

        MqttConfig mqttConfig = MqttUtils.obtainMqttConfig();

        Date expireTime = DateUtils.addDays(new Date(), 30);
        ApplyTokenModel applyTokenModel = ApplyTokenModel.builder()
                .actions("R")
                .resources(mqttConfig.getTopic() + "/#")
                .expireTime(expireTime.getTime())
                .proxyType("MQTT")
                .serviceName("mq")
                .instanceId(mqttConfig.getInstanceId())
                .build();
        String mqttToken = MqttUtils.applyToken(applyTokenModel);
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("R", mqttToken);

        MqttInfo mqttInfo = MqttUtils.obtainMqttInfo(mqttConfig, tokenMap);
        mqttInfo.setExpireTime(expireTime);
        device.setMqttToken(mqttToken);
        device.setMqttClientId(mqttInfo.getClientId());
        DatabaseHelper.update(device);
        return ApiRest.builder().data(mqttInfo).message("获取MqttInfo成功！").successful(true).build();
    }

    /**
     * 上传数据
     *
     * @param uploadDataModel
     * @return
     */
    public ApiRest uploadData(UploadDataModel uploadDataModel) {
        String id = uploadDataModel.getId();
        String deviceCode = uploadDataModel.getDeviceCode();
        Double temperature = uploadDataModel.getTemperature();
        Double humidity = uploadDataModel.getHumidity();

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("deviceCode", deviceCode);
        data.put("temperature", temperature);
        data.put("humidity", humidity);
        PartitionRedisUtils.set(id, JacksonUtils.writeValueAsString(data));
        return ApiRest.builder().message("上传数据成功！").successful(true).build();
    }

    /**
     * 获取客户端数据
     *
     * @param obtainDataModel
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRest obtainData(ObtainDataModel obtainDataModel) {
        String deviceCode = obtainDataModel.getDeviceCode();

        SearchModel searchModel = SearchModel.builder()
                .autoSetDeletedFalse()
                .equal(Device.ColumnName.CODE, deviceCode)
                .build();
        Device device = DatabaseHelper.find(Device.class, searchModel);
        ValidateUtils.notNull(device, "设备不存在！");

        String id = UUID.randomUUID().toString();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", id);

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("data", dataMap);
        payload.put("code", "0000");
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(JacksonUtils.writeValueAsString(payload).getBytes());
        MqttUtils.publish("iot_thc/p2p/" + device.getMqttClientId(), mqttMessage);

        String data = null;
        int times = 120;
        while (times > 0) {
            times--;
            data = PartitionRedisUtils.get(id);
            if (StringUtils.isNotBlank(data)) {
                PartitionRedisUtils.del(id);
                break;
            }
            ThreadUtils.sleepSafe(500);
        }
        ValidateUtils.notBlank(data, "获取设备数据失败！");
        return ApiRest.builder().data(data).message("获取设备数据成功！").successful(true).build();
    }
}
