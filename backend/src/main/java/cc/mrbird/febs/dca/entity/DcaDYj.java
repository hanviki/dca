package cc.mrbird.febs.dca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 学术业绩
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */

@Excel("dca_d_yj")
@Data
@Accessors(chain = true)
public class DcaDYj implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Long id;

    /**
     * 描述
     */
            @ExcelField(value ="描述")
    private String muduleName;

    /**
     * 级别
     */
            @ExcelField(value ="级别")
    private String jb;

    /**
     * 目录
     */
            @ExcelField(value ="目录")
    private String ml;

    /**
     * 编号
     */
            @ExcelField(value ="编号")
    private String bh;

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
            @ExcelField(value ="创建时间")
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
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
     * 父ID
     */
            @ExcelField(value ="父ID")
    private Integer parentId;



    public static final String ID ="id" ;

    public static final String MUDULE_NAME ="mudule_name" ;

    public static final String JB ="jb" ;

    public static final String ML ="ml" ;

    public static final String BH ="bh" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String PARENT_ID ="parent_id" ;

        }