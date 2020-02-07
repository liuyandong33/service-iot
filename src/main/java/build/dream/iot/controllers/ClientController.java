package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import build.dream.common.annotations.PermitAll;
import build.dream.iot.models.client.ObtainDeviceInfoModel;
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
}
