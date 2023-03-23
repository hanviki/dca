package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDOpenclose;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-03-08
 */
public interface DcaDOpencloseMapper extends BaseMapper<DcaDOpenclose> {
        void updateDcaDOpenclose(DcaDOpenclose dcaDOpenclose);
        IPage<DcaDOpenclose> findDcaDOpenclose(Page page, @Param("dcaDOpenclose") DcaDOpenclose dcaDOpenclose);
        }
