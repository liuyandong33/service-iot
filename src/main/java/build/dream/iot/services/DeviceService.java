package build.dream.iot.services;

import build.dream.common.api.ApiRest;
import build.dream.common.domains.iot.Device;
import build.dream.common.utils.*;
import build.dream.iot.constants.Constants;
import build.dream.iot.models.device.DeleteDeviceModel;
import build.dream.iot.models.device.ListDevicesModel;
import build.dream.iot.models.device.SaveDeviceModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Service
public class DeviceService {
    /**
     * 分页查询设备
     *
     * @param listDevicesModel
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRest listDevices(ListDevicesModel listDevicesModel) {
        BigInteger tenantId = listDevicesModel.obtainTenantId();
        BigInteger branchId = listDevicesModel.obtainBranchId();
        int page = listDevicesModel.getPage();
        int rows = listDevicesModel.getRows();

        List<SearchCondition> searchConditions = new ArrayList<SearchCondition>();
        searchConditions.add(new SearchCondition(Device.ColumnName.TENANT_ID, Constants.SQL_OPERATION_SYMBOL_EQUAL, tenantId));
        searchConditions.add(new SearchCondition(Device.ColumnName.BRANCH_ID, Constants.SQL_OPERATION_SYMBOL_EQUAL, branchId));

        SearchModel searchModel = SearchModel.builder()
                .searchConditions(searchConditions)
                .build();

        long count = DatabaseHelper.count(Device.class, searchModel);
        List<Device> devices = null;
        if (count > 0) {
            PagedSearchModel pagedSearchModel = PagedSearchModel.builder()
                    .searchConditions(searchConditions)
                    .page(page)
                    .rows(rows)
                    .build();
            devices = DatabaseHelper.findAllPaged(Device.class, pagedSearchModel);
        } else {
            devices = new ArrayList<Device>();
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", count);
        data.put("rows", devices);
        return ApiRest.builder().data(data).message("查询设备成功！").successful(true).build();
    }

    /**
     * 保存设备
     *
     * @param saveDeviceModel
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiRest saveDevice(SaveDeviceModel saveDeviceModel) {
        BigInteger tenantId = saveDeviceModel.obtainTenantId();
        String tenantCode = saveDeviceModel.obtainTenantCode();
        BigInteger branchId = saveDeviceModel.obtainBranchId();
        BigInteger userId = saveDeviceModel.obtainUserId();
        BigInteger id = saveDeviceModel.getId();
        String name = saveDeviceModel.getName();
        String code = saveDeviceModel.getCode();
        int type = saveDeviceModel.getType();

        Device device = null;
        if (Objects.nonNull(id)) {
            SearchModel searchModel = SearchModel.builder()
                    .equal(Device.ColumnName.TENANT_ID, tenantId)
                    .equal(Device.ColumnName.BRANCH_ID, branchId)
                    .equal(Device.ColumnName.ID, id)
                    .build();
            device = DatabaseHelper.find(Device.class, searchModel);
            ValidateUtils.notNull(device, "设备不存在！");

            device.setName(name);
            device.setCode(code);
            device.setType(type);
            device.setUpdatedUserId(userId);
            DatabaseHelper.update(device);
        } else {
            String clientId = UUID.randomUUID().toString().replace("-", "");
            String clientSecret = UUID.randomUUID().toString().replace("-", "");
            Map<String, String> saveOauthClientDetailRequestParameters = new HashMap<String, String>();
            saveOauthClientDetailRequestParameters.put("clientId", clientId);
            saveOauthClientDetailRequestParameters.put("clientSecret", clientSecret);
            saveOauthClientDetailRequestParameters.put("resourceIds", "device-api");
            saveOauthClientDetailRequestParameters.put("scope", "all");
            saveOauthClientDetailRequestParameters.put("authorizedGrantTypes", "client_credentials");
            saveOauthClientDetailRequestParameters.put("accessTokenValidity", "2592000");
            saveOauthClientDetailRequestParameters.put("refreshTokenValidity", "2592000");
            saveOauthClientDetailRequestParameters.put("userId", userId.toString());
            ApiRest saveOauthClientDetailResult = ProxyUtils.doPostWithRequestParameters(Constants.SERVICE_NAME_PLATFORM, "oauthClientDetail", "saveOauthClientDetail", saveOauthClientDetailRequestParameters);
            ValidateUtils.isTrue(saveOauthClientDetailResult.isSuccessful(), saveOauthClientDetailResult.getError());

            device = Device.builder()
                    .tenantId(tenantId)
                    .tenantCode(tenantCode)
                    .branchId(branchId)
                    .name(name)
                    .code(code)
                    .type(type)
                    .createdUserId(userId)
                    .updatedUserId(userId)
                    .build();
            DatabaseHelper.insert(device);
        }

        return ApiRest.builder().data(device).message("保存设备成功！").successful(true).build();
    }

    /**
     * 删除设备
     *
     * @param deleteDeviceModel
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiRest deleteDevice(DeleteDeviceModel deleteDeviceModel) {
        BigInteger tenantId = deleteDeviceModel.obtainTenantId();
        BigInteger branchId = deleteDeviceModel.obtainBranchId();
        BigInteger userId = deleteDeviceModel.obtainUserId();
        BigInteger deviceId = deleteDeviceModel.getDeviceId();

        SearchModel searchModel = SearchModel.builder()
                .equal(Device.ColumnName.TENANT_ID, tenantId)
                .equal(Device.ColumnName.BRANCH_ID, branchId)
                .equal(Device.ColumnName.ID, deviceId)
                .build();

        Device device = DatabaseHelper.find(Device.class, searchModel);
        ValidateUtils.notNull(device, "设备不存在！");

        device.setUpdatedUserId(userId);
        device.setUpdatedRemark("删除设备！");
        device.setDeletedTime(new Date());
        device.setDeleted(true);
        DatabaseHelper.update(device);
        return ApiRest.builder().message("删除设备成功！").successful(true).build();
    }
}
