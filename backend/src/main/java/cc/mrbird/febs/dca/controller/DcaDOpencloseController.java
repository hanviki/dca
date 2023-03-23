package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import cc.mrbird.febs.dca.service.IDcaDOpencloseService;
import cc.mrbird.febs.dca.entity.DcaDOpenclose;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2023-03-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDOpenclose")

public class DcaDOpencloseController extends BaseController {

    private String message;
    @Autowired
    public IDcaDOpencloseService iDcaDOpencloseService;
    @Autowired
    public IDcaDYearsettingService iDcaDYearsettingService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaDOpenclose/DcaDOpenclose','dca/DcaDOpenclose/DcaDOpenclose','dcaDOpenclose:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaDOpenclose:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaDOpenclose:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaDOpenclose:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request       分页信息
     * @param dcaDOpenclose 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, DcaDOpenclose dcaDOpenclose) {
        return getDataTable(this.iDcaDOpencloseService.findDcaDOpencloses(request, dcaDOpenclose));
    }

    /**
     * 添加
     *
     * @param dcaDOpenclose
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addDcaDOpenclose(@Valid DcaDOpenclose dcaDOpenclose) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaDOpenclose.setCreateUserId(currentUser.getUserId());
            this.iDcaDOpencloseService.createDcaDOpenclose(dcaDOpenclose);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaDOpenclose
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateDcaDOpenclose(@Valid DcaDOpenclose dcaDOpenclose) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaDOpenclose.setModifyUserId(currentUser.getUserId());
            this.iDcaDOpencloseService.updateDcaDOpenclose(dcaDOpenclose);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @PutMapping("open")
    public void deleteDcaDOpencloses( String ids, int state) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            for (String id : arr_ids
            ) {
                DcaDOpenclose oc = this.iDcaDOpencloseService.getById(id);

                /**
                 * 开启或者关闭前 都删除已经存在的记录
                 */
                LambdaQueryWrapper<DcaDYearsetting> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(DcaDYearsetting::getDcaYear, oc.getDcaYear());
                queryWrapper.eq(DcaDYearsetting::getGwdj, handleDj(oc.getGwdj()));
                List<DcaDYearsetting> listYear = this.iDcaDYearsettingService.list(queryWrapper);

                if (listYear.size() > 0) {
                    List<String> arrids = listYear.stream().map(p -> p.getId().toString()).collect(Collectors.toList());
                    this.iDcaDYearsettingService.deleteDcaDYearsettings(arrids.toArray(new String[arrids.size()]));
                }
                if (state == 2) {
                    DcaDYearsetting dcaDYearsetting = new DcaDYearsetting();
                    dcaDYearsetting.setDcaYear(oc.getDcaYear());
                    dcaDYearsetting.setGwdj(handleDj(oc.getGwdj()));
                    dcaDYearsetting.setIsDeletemark(1);
                    this.iDcaDYearsettingService.createDcaDYearsetting(dcaDYearsetting);
                }


                oc.setState(state);
                this.iDcaDOpencloseService.updateDcaDOpenclose(oc);
            }

        } catch (Exception e) {
            message = "更新失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    private String handleDj(String gwdj) {
        if (gwdj.equals("高级")) {
            return "高级";
        }
        if (gwdj.equals("中初级")) {
            return "中级";
        }
        return "初级";
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaDOpenclose:export")
    public void export(QueryRequest request, DcaDOpenclose dcaDOpenclose, HttpServletResponse response) throws FebsException {
        try {
            List<DcaDOpenclose> dcaDOpencloses = this.iDcaDOpencloseService.findDcaDOpencloses(request, dcaDOpenclose).getRecords();
            ExcelKit.$Export(DcaDOpenclose.class, response).downXlsx(dcaDOpencloses, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public DcaDOpenclose detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaDOpenclose dcaDOpenclose = this.iDcaDOpencloseService.getById(id);
        return dcaDOpenclose;
    }
}