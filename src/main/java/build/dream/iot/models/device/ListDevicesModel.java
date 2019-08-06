package build.dream.iot.models.device;

import build.dream.common.models.weixinpay.IotBasicModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ListDevicesModel extends IotBasicModel {
    @NotNull
    @Min(value = 1)
    private Integer page;

    @NotNull
    @Min(value = 1)
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
