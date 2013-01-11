/**
 * 
 */
package jabara.sample.service.impl;

import jabara.sample.entity.EEmployee;

import java.util.List;

import org.apache.ibatis.annotations.Select;

/**
 * @author jabaraster
 */
public interface IEmployeeMapper {

    /**
     * @return -
     */
    @Select("select * from eemployee")
    List<EEmployee> getAll();
}
