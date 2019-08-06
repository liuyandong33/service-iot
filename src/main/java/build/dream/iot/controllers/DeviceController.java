package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
    @RequestMapping(value = "/listDevices")
    @ResponseBody
    @ApiRestAction
    public String listDevices() {
        return null;
    }
}
