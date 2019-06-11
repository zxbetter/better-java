package cn.zxbetter.happy.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangxin
 *
 * DO 的基类
 */
@Getter
@Setter
@ToString
public class BaseDO implements Serializable {
    /**
     * 删除标识
     */
    @Column(name = "delete_flag")
    private String deleteFlag;

    /**
     * 创建时间
     */
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 版本号
     */
    @Version
    @Column(name = "version_number")
    private Long versionNumber;

    public static final String DELETE_FLAG = "deleteFlag";

    public static final String DB_DELETE_FLAG = "delete_flag";

    public static final String CREATION_TIME = "creationTime";

    public static final String DB_CREATION_TIME = "creation_time";

    public static final String UPDATE_TIME = "updateTime";

    public static final String DB_UPDATE_TIME = "update_time";

    public static final String VERSION_NUMBER = "versionNumber";

    public static final String DB_VERSION_NUMBER = "version_number";
}
