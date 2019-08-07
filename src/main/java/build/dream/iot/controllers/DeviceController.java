package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import build.dream.iot.models.device.DeleteDeviceModel;
import build.dream.iot.models.device.ListDevicesModel;
import build.dream.iot.models.device.SaveDeviceModel;
import build.dream.iot.services.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
    /**
     * 分页查询设备
     *
     * @return
     */
    @RequestMapping(value = "/listDevices")
    @ResponseBody
    @ApiRestAction(modelClass = ListDevicesModel.class, serviceClass = DeviceService.class, serviceMethodName = "listDevices", error = "查询设备失败")
    public String listDevices() {
        return null;
    }

    /**
     * 保存设备
     *
     * @return
     */
    @RequestMapping(value = "/saveDevice")
    @ResponseBody
    @ApiRestAction(modelClass = SaveDeviceModel.class, serviceClass = DeviceService.class, serviceMethodName = "saveDevice", error = "保存设备失败")
    public String saveDevice() {
        return null;
    }

    /**
     * 删除设备
     *
     * @return
     */
    @RequestMapping(value = "/deleteDevice")
    @ResponseBody
    @ApiRestAction(modelClass = DeleteDeviceModel.class, serviceClass = DeviceService.class, serviceMethodName = "deleteDevice", error = "删除设备失败")
    public String deleteDevice() {
        return null;
    }
}
