package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaDOpenclose;
import cc.mrbird.febs.dca.dao.DcaDOpencloseMapper;
import cc.mrbird.febs.dca.service.IDcaDOpencloseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-03-08
 */
@Slf4j
@Service("IDcaDOpencloseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDOpencloseServiceImpl extends ServiceImpl<DcaDOpencloseMapper, DcaDOpenclose> implements IDcaDOpencloseService {


@Override
public IPage<DcaDOpenclose> findDcaDOpencloses(QueryRequest request, DcaDOpenclose dcaDOpenclose){
        try{
        LambdaQueryWrapper<DcaDOpenclose> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDOpenclose::getIsDeletemark, 1);//1是未删 0是已删
        if(dcaDOpenclose.getState()!=null ){
            queryWrapper.eq(DcaDOpenclose::getState,dcaDOpenclose.getState());
        }

        Page<DcaDOpenclose> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDOpenclose> findDcaDOpencloseList (QueryRequest request, DcaDOpenclose dcaDOpenclose){
        try{
        Page<DcaDOpenclose> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDOpenclose(page,dcaDOpenclose);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDOpenclose(DcaDOpenclose dcaDOpenclose){
                dcaDOpenclose.setCreateTime(new Date());
        dcaDOpenclose.setIsDeletemark(1);
        this.save(dcaDOpenclose);
        }

@Override
@Transactional
public void updateDcaDOpenclose(DcaDOpenclose dcaDOpenclose){
        dcaDOpenclose.setModifyTime(new Date());
        this.baseMapper.updateDcaDOpenclose(dcaDOpenclose);
        }

@Override
@Transactional
public void deleteDcaDOpencloses(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaDOpenclose> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaDOpenclose> queryWrapper=new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaDOpenclose::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }