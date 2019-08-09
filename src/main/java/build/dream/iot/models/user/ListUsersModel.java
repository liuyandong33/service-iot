package build.dream.iot.models.user;

import build.dream.common.models.IotBasicModel;

import javax.validation.constraints.NotNull;

public class ListUsersModel extends IotBasicModel {
    @NotNull
    private Integer page;

    @NotNull
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
