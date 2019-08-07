package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import build.dream.iot.models.device.ListDevicesModel;
import build.dream.iot.services.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
    @RequestMapping(value = "/listDevices")
    @ResponseBody
    @ApiRestAction(modelClass = ListDevicesModel.class, serviceClass = DeviceService.class, serviceMethodName = "listDevices", error = "查询设备成功")
    public String listDevices() {
        return null;
    }
}
