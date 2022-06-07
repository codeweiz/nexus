package cn.microboat;

import lombok.*;

import java.io.Serializable;

/**
 * @author zhouwei
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Hello implements Serializable {
    private String message;
    private String description;
}
