package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetPropDto extends PageSearchDto implements Serializable {
    private static final long serialVersionUID = 2396654715019746670L;
    @ApiModelProperty(value = "宠物ID", required = false)
    private Integer petId;
}
