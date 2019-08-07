package build.dream.iot.services;

import build.dream.common.api.ApiRest;
import build.dream.common.iot.domains.Device;
import build.dream.common.utils.*;
import build.dream.iot.constants.Constants;
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
            device = Device.builder()
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
}
