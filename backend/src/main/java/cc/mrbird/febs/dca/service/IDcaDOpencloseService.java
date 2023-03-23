package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDOpenclose;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2023-03-08
 */
public interface IDcaDOpencloseService extends IService<DcaDOpenclose> {

        IPage<DcaDOpenclose> findDcaDOpencloses(QueryRequest request, DcaDOpenclose dcaDOpenclose);

        IPage<DcaDOpenclose> findDcaDOpencloseList(QueryRequest request, DcaDOpenclose dcaDOpenclose);

        void createDcaDOpenclose(DcaDOpenclose dcaDOpenclose);

        void updateDcaDOpenclose(DcaDOpenclose dcaDOpenclose);

        void deleteDcaDOpencloses(String[]Ids);

        List<DcaDOpenclose> getAll(String userAccount,String dcaYear);
        }
