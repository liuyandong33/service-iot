package build.dream.iot.controllers;

import build.dream.common.annotations.PermitAll;
import build.dream.iot.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ping")
@PermitAll
public class PingController {
    @RequestMapping(value = "/ok")
    @ResponseBody
    public String ok() {
        return Constants.OK;
    }
}
