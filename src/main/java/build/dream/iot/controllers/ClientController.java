package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import build.dream.common.annotations.PermitAll;
import build.dream.iot.models.client.ObtainDataModel;
import build.dream.iot.models.client.ObtainDeviceInfoModel;
import build.dream.iot.models.client.ObtainMqttInfoModel;
import build.dream.iot.models.client.UploadDataModel;
import build.dream.iot.services.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/client")
public class ClientController {
    /**
     * @return
     */
    @PermitAll
    @RequestMapping(value = "/obtainDeviceInfo")
    @ResponseBody
    @ApiRestAction(modelClass = ObtainDeviceInfoModel.class, serviceClass = ClientService.class, serviceMethodName = "obtainDeviceInfo", error = "获取设备信息失败")
    public String obtainDeviceInfo() {
        return null;
    }

    /**
     * 获取MqttInfo
     *
     * @return
     */
    @PermitAll
    @RequestMapping(value = "/obtainMqttInfo")
    @ResponseBody
    @ApiRestAction(modelClass = ObtainMqttInfoModel.class, serviceClass = ClientService.class, serviceMethodName = "obtainMqttInfo", error = "获取MqttInfo失败")
    public String obtainMqttInfo() {
        return null;
    }

    /**
     * 上传数据
     *
     * @return
     */
    @PermitAll
    @RequestMapping(value = "/uploadData")
    @ResponseBody
    @ApiRestAction(modelClass = UploadDataModel.class, serviceClass = ClientService.class, serviceMethodName = "uploadData", error = "上传数据失败")
    public String uploadData() {
        return null;
    }

    @PermitAll
    @RequestMapping(value = "/obtainData")
    @ResponseBody
    @ApiRestAction(modelClass = ObtainDataModel.class, serviceClass = ClientService.class, serviceMethodName = "obtainData", error = "获取数据失败")
    public String obtainData() {
        return null;
    }
}
