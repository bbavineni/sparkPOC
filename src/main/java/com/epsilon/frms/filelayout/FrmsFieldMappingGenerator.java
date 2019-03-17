package com.epsilon.frms.filelayout;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrmsFieldMappingGenerator {

    public static List<FieldMapping> getFRMSFieldMapping() {
        List<FieldMapping> fieldMappings = new ArrayList<FieldMapping>();

        // Gson g = new Gson();
        // FieldMapping fieldMapping = g.fromJson(jsonString, FieldMapping.class)

        List<InputField> inputFields = getInputFields();
        List<OutputField> outputFields = getOutputFields();

        inputFields.forEach(inputField -> {
            fieldMappings.add(FieldMapping.builder().mappingId(inputField.getFieldLayoutId())
                    .inputField(inputField)
                    .outputField(outputFields.get(inputField.getFieldOrder() - 1))
                    .outputFieldTransformation(inputField.getFieldName().equalsIgnoreCase("EmailAddress") ? "LOWER" : null)
                    .build());
        });

        return fieldMappings;
    }

    private static List<InputField> getInputFields() {

        List<InputField> inputFields = new ArrayList<InputField>();

        inputFields.add(InputField.builder().fieldName("CustomerKey")
                .fieldLayoutId("3").fieldOrder(1).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("reference_number")
                .fieldLayoutId("4").fieldOrder(2).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("onca_POC_level_1_desc")
                .fieldLayoutId("5").fieldOrder(3).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("date_created")
                .fieldLayoutId("6").fieldOrder(4).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("contact_first_name")
                .fieldLayoutId("7").fieldOrder(5).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("EmailAddress")
                .fieldLayoutId("8").fieldOrder(6).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("order_id")
                .fieldLayoutId("9").fieldOrder(7).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("status_descripton")
                .fieldLayoutId("10").fieldOrder(8).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("user_code")
                .fieldLayoutId("11").fieldOrder(9).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("onca_disp_level_1_desc")
                .fieldLayoutId("12").fieldOrder(10).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("EMAIL2")
                .fieldLayoutId("13").fieldOrder(11).fieldDataType("String").build());

        inputFields.add(InputField.builder().fieldName("HASHED_EMAIL_ADDR")
                .fieldLayoutId("14").fieldOrder(12).fieldDataType("String").build());

        return inputFields;
    }

    private static List<OutputField> getOutputFields() {

        List<OutputField> outputFields = new ArrayList<OutputField>();

        outputFields.add(OutputField.builder().fieldName("CustomerKey")
                .fieldLayoutId("3").fieldOrder(1).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("reference_number")
                .fieldLayoutId("4").fieldOrder(2).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("onca_POC_level_1_desc")
                .fieldLayoutId("5").fieldOrder(3).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("date_created")
                .fieldLayoutId("6").fieldOrder(4).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("contact_first_name")
                .fieldLayoutId("7").fieldOrder(5).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("EmailAddress")
                .fieldLayoutId("8").fieldOrder(6).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("order_id")
                .fieldLayoutId("9").fieldOrder(7).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("status_descripton")
                .fieldLayoutId("10").fieldOrder(8).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("user_code")
                .fieldLayoutId("11").fieldOrder(9).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("onca_disp_level_1_desc")
                .fieldLayoutId("12").fieldOrder(10).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("EMAIL2")
                .fieldLayoutId("13").fieldOrder(11).fieldDataType("String").build());

        outputFields.add(OutputField.builder().fieldName("HASHED_EMAIL_ADDR")
                .fieldLayoutId("14").fieldOrder(12).fieldDataType("String").build());

        return outputFields;
    }

}
