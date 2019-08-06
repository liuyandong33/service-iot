package build.dream.iot.services;

import build.dream.common.api.ApiRest;
import build.dream.common.iot.domains.Device;
import build.dream.common.utils.DatabaseHelper;
import build.dream.common.utils.PagedSearchModel;
import build.dream.common.utils.SearchCondition;
import build.dream.common.utils.SearchModel;
import build.dream.iot.constants.Constants;
import build.dream.iot.models.device.ListDevicesModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceService {
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
}
