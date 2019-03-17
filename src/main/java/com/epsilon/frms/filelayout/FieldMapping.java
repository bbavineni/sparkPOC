package com.epsilon.frms.filelayout;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldMapping {

    String mappingId;

    InputField inputField;

    OutputField outputField;

    String outputFieldTransformation;

}
