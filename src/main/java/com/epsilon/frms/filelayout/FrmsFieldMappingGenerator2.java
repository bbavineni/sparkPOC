package com.epsilon.frms.filelayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrmsFieldMappingGenerator2 {

    public static List<FieldMapping> getFRMSFieldMapping(int fileNumber, String delimiter) {
        List<FieldMapping> fieldMappings = new ArrayList<FieldMapping>();
        String columnNames = getColumNames(fileNumber);
        // Gson g = new Gson();
        // FieldMapping fieldMapping = g.fromJson(jsonString, FieldMapping.class)
        List<String> columnsList = Arrays.asList(columnNames.split(delimiter));
        List<InputField> inputFields = getInputFields(columnsList);
        List<OutputField> outputFields = getOutputFields(columnsList);

        inputFields.forEach(inputField -> {
            fieldMappings.add(FieldMapping.builder().mappingId(inputField.getFieldLayoutId())
                    .inputField(inputField)
                    .outputField(outputFields.get(inputField.getFieldOrder()))
                    .outputFieldTransformation(inputField.getFieldName().equalsIgnoreCase("EmailAddress") ? "LOWER" : null)
                    .build());
        });

        return fieldMappings;
    }

    private static String getColumNames(int fileNumber) {
        switch (fileNumber) {
            case 1:
                return "CustomerKey|Source|FirstName|LastName|AddressLine1|AddressLine2|City|State|PostalCode|EmailAddress|AgilityID|Field12";
            case 2:
                return "CustomerKey,SSN,EmailAddress,Gender,FirstName,MiddleInitial,LastName,AddressLine1,City,StateCode,PostalCode,CountryCode,PhoneNumber," +
                        "Credit_Card,Sport,SUBJECTLINE,URLLINK,DESKTOPASSETNAME,LEAD_KEY,Amount_of_Purchase,PREHEADER,EXCLUSIONS,RUS_EARNED,OFFERID" +
                        ",CMPNAME";
            default:
                return "";
        }
    }

    private static List<InputField> getInputFields(List<String> columnsList) {

        List<InputField> inputFields = new ArrayList<InputField>();
        for (int i = 0; i < columnsList.size(); i++) {
            inputFields.add(InputField.builder().fieldName(columnsList.get(i))
                    .fieldLayoutId(i + "").fieldOrder(i).fieldDataType("String").build());
        }
        // columnsList.forEach(column -> {
        // inputFields.add(InputField.builder().fieldName(column)
        // .fieldLayoutId(inputFields.size() + 1 + "").fieldOrder(inputFields.size() + 1).fieldDataType("String").build());
        // });

        return inputFields;
    }

    private static List<OutputField> getOutputFields(List<String> columnsList) {

        List<OutputField> outputFields = new ArrayList<OutputField>();

        for (int i = 0; i < columnsList.size(); i++) {
            outputFields.add(OutputField.builder().fieldName(columnsList.get(i))
                    .fieldLayoutId(i + "").fieldOrder(i).fieldDataType("String").build());
        }

        // columnsList.forEach(column -> {
        // outputFields.add(OutputField.builder().fieldName(column)
        // .fieldLayoutId(outputFields.size() + 1 + "").fieldOrder(outputFields.size() + 1).fieldDataType("String").build());
        // });

        return outputFields;
    }

    public static FieldMapping getFieldMappingByInputField(List<FieldMapping> fieldMappings, String inputFieldLayoutId) {

        for (int i = 0; i < fieldMappings.size(); i++) {
            if (fieldMappings.get(i).getInputField().getFieldLayoutId().equals(inputFieldLayoutId)) {
                return fieldMappings.get(i);
            }
        }

        return null;

    }

}
