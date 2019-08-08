package build.dream.iot.controllers;

import build.dream.common.annotations.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PermitAll
@Controller
@RequestMapping(value = "/land")
public class LandController {
    @RequestMapping(value = "/upload")
    public String upload() {
        return "ping/upload";
    }
}
