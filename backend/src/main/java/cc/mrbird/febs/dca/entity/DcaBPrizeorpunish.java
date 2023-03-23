package cc.mrbird.febs.dca.entity;

import java.time.LocalDateTime;

import cc.mrbird.febs.common.converter.AuditStateConverter;
import cc.mrbird.febs.common.converter.BooleanConverter;
import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.StateConverter;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 何时受奖励处分
 * </p>
 *
 * @author viki
 * @since 2020-12-28
 */

@Excel("dca_b_prizeorpunish")
@Data
@Accessors(chain = true)
public class DcaBPrizeorpunish implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelField(value = "主键")
    private String id;

    /**
     * 附件
     */
    @ExcelField(value = "附件")
    private String fileId;

    /**
     * 排序
     */
    @ExcelField(value = "排序")
    private Integer displayIndex;

    /**
     * 附件地址
     */
    @ExcelField(value = "附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
    @ExcelField(value = "人事编号")
    private String userAccount;

    /**
     * 开始时间
     */
    @ExcelField(value = "开始时间", writeConverter = DateConverter.class)
    private Date ppStartTime;
    private transient String ppStartTimeFrom;
    private transient String ppStartTimeTo;

    /**
     * 结束时间
     */
    @ExcelField(value = "结束时间", writeConverter = DateConverter.class)
    private Date ppEndTime;
    private transient String ppEndTimeFrom;
    private transient String ppEndTimeTo;

    /**
     * 类别
     */
    @ExcelField(value = "类别")
    private String ppLb;

    /**
     * 授奖部门
     */
    @ExcelField(value = "授奖部门")
    private String ppPartment;

    /**
     * 奖励/处分
     */
    @ExcelField(value = "奖励/处分")
    private String ppCategory;

    /**
     * 奖励名称
     */
    @ExcelField(value = "奖励名称")
    private String ppContent;

    /**
     * 状态
     */
    @ExcelField(value = "状态", writeConverter = StateConverter.class)
    private Integer state;

    /**
     * 审核状态
     */
    @ExcelField(value = "审核状态", writeConverter = AuditStateConverter.class)
    private Integer auditState;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
    @ExcelField(value = "是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = DateConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
    @ExcelField(value = "创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
    @ExcelField(value = "修改人")
    private Long modifyUserId;

    /**
     * 审核人
     */
    @TableField("auditMan")
    @ExcelField(value = "审核人")
    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")
    @ExcelField(value = "审核人姓名")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
    @ExcelField(value = "审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
    @ExcelField(value = "审核意见")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
    @ExcelField(value = "经审核是否构成职称晋升条件",writeConverter = BooleanConverter.class)
    private Boolean IsUse;

    private  transient Integer auditXuhao;
    private  transient  Integer auditXuhaoS;
    private  transient  Integer auditXuhaoE;


    public static final String ID = "id";

    public static final String FILE_ID = "file_id";

    public static final String DISPLAY_INDEX = "display_index";

    public static final String FILE_URL = "file_url";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String PP_START_TIME = "pp_start_time";

    public static final String PP_END_TIME = "pp_end_time";

    public static final String PP_LB = "pp_lb";

    public static final String PP_PARTMENT = "pp_partment";

    public static final String PP_CATEGORY = "pp_category";

    public static final String PP_CONTENT = "pp_content";

    public static final String STATE = "state";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

    public static final String AUDITMAN = "auditMan";

    public static final String AUDITMANNAME = "auditManName";

    public static final String AUDITDATE = "auditDate";

    public static final String AUDITSUGGESTION = "auditSuggestion";

    public static final String ISUSE = "IsUse";

}