package build.dream.iot.controllers;

import build.dream.common.annotations.ApiRestAction;
import build.dream.common.annotations.PermitAll;
import build.dream.iot.models.user.AddUserModel;
import build.dream.iot.models.user.ListUsersModel;
import build.dream.iot.models.user.ObtainBranchInfoModel;
import build.dream.iot.models.user.ObtainUserInfoModel;
import build.dream.iot.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    /**
     * 查询用户列表
     *
     * @return
     */
    @RequestMapping(value = "/listUsers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiRestAction(modelClass = ListUsersModel.class, serviceClass = UserService.class, serviceMethodName = "listUsers", error = "查询员工列表失败")
    public String listUsers() {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/obtainUserInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiRestAction(modelClass = ObtainUserInfoModel.class, serviceClass = UserService.class, serviceMethodName = "obtainUserInfo", error = "获取员工信息失败")
    public String obtainUserInfo() {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @PermitAll
    @RequestMapping(value = "/obtainBranchInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiRestAction(modelClass = ObtainBranchInfoModel.class, serviceClass = UserService.class, serviceMethodName = "obtainBranchInfo", error = "获取门店信息失败")
    public String obtainBranchInfo() {
        return null;
    }

    /**
     * 增加员工
     *
     * @return
     */
    @RequestMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiRestAction(modelClass = AddUserModel.class, serviceClass = UserService.class, serviceMethodName = "addUser", error = "增加员工失败")
    public String addUser() {
        return null;
    }
}
