package cc.mrbird.febs.doctor.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 年度考核
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */

@Excel("dca_b_doc_auditfive")
@Data
@Accessors(chain = true)
public class DcaBDocAuditfive implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 附件
     */
        
        @ExcelField(value ="附件")
    private String fileId;

    /**
     * 考核结果
     */
        
        @ExcelField(value ="考核结果")
    private String khjg;

    /**
     * 起止年度
     */
        
        @ExcelField(value ="起止年度")
    private String year;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 政治表现
     */
        
        @ExcelField(value ="政治表现")
    private String zzbx;

    /**
     * 职业道德
     */
        
        @ExcelField(value ="职业道德")
    private String zydd;

    /**
     * 廉洁自律
     */
        
        @ExcelField(value ="廉洁自律")
    private String ljzl;

    /**
     * 业务能力工作态度总结
     */
        
        @ExcelField(value ="业务能力工作态度总结")
    private String ywnlgztd;

    /**
     * 个人科研进展较为详细的成果总结
     */
        
        @ExcelField(value ="个人科研进展较为详细的成果总结")
    private String grkyjz;

    /**
     * 下年度科研课题较为详细计划
     */
        
        @ExcelField(value ="下年度科研课题较为详细计划")
    private String xndkykt;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String adContent;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        
        @ExcelField(value ="创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        
        @ExcelField(value ="修改人")
    private Long modifyUserId;

    /**
     * 审核人
     */
    @TableField("auditMan")
        
        @ExcelField(value ="审核人")
    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")
        
        @ExcelField(value ="审核人姓名")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
        
        @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String KHJG ="khjg" ;

    public static final String YEAR ="year" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String ZZBX ="zzbx" ;

    public static final String ZYDD ="zydd" ;

    public static final String LJZL ="ljzl" ;

    public static final String YWNLGZTD ="ywnlgztd" ;

    public static final String GRKYJZ ="grkyjz" ;

    public static final String XNDKYKT ="xndkykt" ;

    public static final String AD_CONTENT ="ad_content" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

        }