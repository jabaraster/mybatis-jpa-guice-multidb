/**
 * 
 */
package jabara.sample.service.impl;

import jabara.sample.entity.EKeyword;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author jabaraster
 */
public interface IKeywordMapper {

    /**
     * @param pId -
     * @return -
     */
    List<EKeyword> findById(@Param("pId") long pId);
}
