package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.ExportExcelUtils;

import cc.mrbird.febs.doctor.service.IDcaBDocScientificprizeService;
import cc.mrbird.febs.doctor.entity.DcaBDocScientificprize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
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

/**
 *
 * @author viki
 * @since 2021-01-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDocScientificprize")

public class DcaBDocScientificprizeController extends BaseController{

private String message;
@Autowired
public IDcaBDocScientificprizeService iDcaBDocScientificprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocScientificprize 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize){
        return getDataTable(this.iDcaBDocScientificprizeService.findDcaBDocScientificprizes(request, dcaBDocScientificprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBDocScientificprize.setUserAccount(currentUser.getUsername());
    //dcaBDocScientificprize.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocScientificprizeService.findDcaBDocScientificprizes(request, dcaBDocScientificprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize){
        User currentUser= FebsUtil.getCurrentUser();
    //dcaBDocScientificprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocScientificprizeService.findDcaBDocScientificprizes(request, dcaBDocScientificprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBDocScientificprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBDocScientificprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBDocScientificprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBDocScientificprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBDocScientificprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBDocScientificprize dcaBDocScientificprize:list
        ){
        if(dcaBDocScientificprize.getState()!=null&&dcaBDocScientificprize.getState().equals(3)) {
    dcaBDocScientificprize.setState(3);
        }
        else{
    dcaBDocScientificprize.setState(state);
        }
    dcaBDocScientificprize.setDisplayIndex(display);
        display+=1;
    dcaBDocScientificprize.setCreateUserId(currentUser.getUserId());
    dcaBDocScientificprize.setUserAccount(currentUser.getUsername());
    dcaBDocScientificprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBDocScientificprizeService.createDcaBDocScientificprize(dcaBDocScientificprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBDocScientificprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBDocScientificprize dcaBDocScientificprize= JSON.parseObject(jsonStr, new TypeReference<DcaBDocScientificprize>() {
        });
    dcaBDocScientificprize.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBDocScientificprize.setAuditState(0);
        }
        else {
    dcaBDocScientificprize.setAuditState(auditState+1);
        }

        }*/
    dcaBDocScientificprize.setAuditMan(currentUser.getUsername());
    dcaBDocScientificprize.setAuditManName(currentUser.getRealname());
    dcaBDocScientificprize.setAuditDate(DateUtil.date());
        this.iDcaBDocScientificprizeService.updateDcaBDocScientificprize(dcaBDocScientificprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBDocScientificprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocScientificprize(@Valid DcaBDocScientificprize dcaBDocScientificprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocScientificprize.setCreateUserId(currentUser.getUserId());
    dcaBDocScientificprize.setUserAccount(currentUser.getUsername());
        this.iDcaBDocScientificprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBDocScientificprizeService.createDcaBDocScientificprize(dcaBDocScientificprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocScientificprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBDocScientificprize:update")
public void updateDcaBDocScientificprize(@Valid DcaBDocScientificprize dcaBDocScientificprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBDocScientificprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocScientificprizeService.updateDcaBDocScientificprize(dcaBDocScientificprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBDocScientificprize:delete")
public void deleteDcaBDocScientificprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocScientificprizeService.deleteDcaBDocScientificprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    //dcaBDocScientificprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBDocScientificprize> dcaBDocScientificprizeList=  this.iDcaBDocScientificprizeService.findDcaBDocScientificprizes(request, dcaBDocScientificprize).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBDocScientificprizeList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBDocScientificprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBDocScientificprize dcaBDocScientificprize=this.iDcaBDocScientificprizeService.getById(id);
        return dcaBDocScientificprize;
        }
        }