package build.dream.iot.models.device;

import build.dream.common.models.IotBasicModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public class SaveDeviceModel extends IotBasicModel {
    private BigInteger id;

    @NotNull
    @Length(max = 20)
    private String name;

    @NotNull
    @Length(max = 50)
    private String code;

    @NotNull
    private Integer type;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
