package cn.microboat;

import lombok.*;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhouwei
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Hello implements Serializable {
    /**
     * 序列号 id
     */
    private static final long serialVersionUID = 725745410605631233L;

    /**
     * 消息
     */
    private String message;

    /**
     * 描述
     */
    private String description;
}
