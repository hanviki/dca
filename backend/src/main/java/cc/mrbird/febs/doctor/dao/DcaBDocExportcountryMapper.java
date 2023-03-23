package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocExportcountry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 出国情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocExportcountryMapper extends BaseMapper<DcaBDocExportcountry> {
        void updateDcaBDocExportcountry(DcaBDocExportcountry dcaBDocExportcountry);
        IPage<DcaBDocExportcountry> findDcaBDocExportcountry(Page page, @Param("dcaBDocExportcountry") DcaBDocExportcountry dcaBDocExportcountry);

@Update(" update dca_b_doc_exportcountry set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_exportcountry  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
