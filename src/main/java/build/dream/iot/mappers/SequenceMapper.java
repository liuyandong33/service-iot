package build.dream.iot.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SequenceMapper {
    Integer nextValue(@Param("sequenceName") String sequenceName);

    Integer currentValue(@Param("sequenceName") String sequenceName);

    Integer currentValueToday(@Param("sequenceName") String sequenceName);

    Integer nextValueToday(@Param("sequenceName") String sequenceName);
}
