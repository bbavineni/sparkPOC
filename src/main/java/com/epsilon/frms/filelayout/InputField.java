package com.epsilon.frms.filelayout;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InputField {

    String fieldName;

    String fieldLayoutId;

    int fieldOrder;

    String fieldDataType;

}
