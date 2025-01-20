 

//    public static void main(String[] args) throws Exception {
//        // Path to the JSON file
//        File jsonFile = new File("C:\\Users\\Kishore kumar\\eclipse-workspace\\spel\\src\\listInput.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Load JSON into a JsonNode
//        JsonNode rootNode = objectMapper.readTree(jsonFile);
//
//        // Generate SpEL expressions for all keys
//        List<String> spelExpressions = generateSpelExpressionsForKeys(rootNode, "");
//
//        // Print all generated SpEL expressions
//        spelExpressions.forEach(System.out::println);
//    }
//
//    public static List<String> generateSpelExpressionsForKeys(JsonNode node, String parentPath) {
//        List<String> expressions = new ArrayList<>();
//
//        Iterator<String> fieldNames = node.fieldNames();
//        while (fieldNames.hasNext()) {
//            String fieldName = fieldNames.next();
//
//            // Construct the current JSON path
//            String currentPath = parentPath.isEmpty() ? fieldName : parentPath + "." + fieldName;
//
//            // Generate SpEL expression for the current key
//            String spelExpression = "#json['" + currentPath.replace(".", "']['") + "'] != null";
//            expressions.add(spelExpression);
//
//            // Recursively process nested objects
//            if (node.get(fieldName).isObject()) {
//                expressions.addAll(generateSpelExpressionsForKeys(node.get(fieldName), currentPath));
//            }
//
//            // Process arrays
//            if (node.get(fieldName).isArray()) {
//                int index = 0;
//                for (JsonNode arrayElement : node.get(fieldName)) {
//                    if (arrayElement.isObject()) {
//                        expressions.addAll(generateSpelExpressionsForKeys(arrayElement, currentPath + "[" + index + "]"));
//                    }
//                    index++;
//                }
//            }
//        }
//
//        return expressions;
//    }
//}

package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpelCon {

    public static void main(String[] args) throws Exception {
        File jsonFile = new File("C:\\Users\\Kishore kumar\\eclipse-workspace\\spel\\src\\listInput.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonFile);

        Map<String, String> regexPatterns = new HashMap<>();
        regexPatterns.put("context", "^[a-zA-Z0-9_]+$");
        regexPatterns.put("customerId", "^[a-zA-Z0-9_]+$"); 
        regexPatterns.put("productCode", "^[a-zA-Z0-9_]+$");   
//        regexPatterns.put("pagination.pageSize", "^[0-9{2}]+$");
//        regexPatterns.put("pagination.pageIndex", "^[0-9{1}]+$"); 
        regexPatterns.put("sorting.key", "^[a-zA-Z0-9_]+$"); 
        regexPatterns.put("sorting.value", "^[a-zA-Z0-9_]+$"); 

        boolean isValid = validateValues(rootNode, "", regexPatterns);

        System.out.println(isValid ? "Valid JSON : All the JSON keys are Valid based on the regex pattern." : "Invalid JSON : Above the JSON keys are invalid based on the regex pattern.");
    }

    public static boolean validateValues(JsonNode node, String parentPath, Map<String, String> regexPatterns) {
        Iterator<String> fieldNames = node.fieldNames();
        boolean isValid = true;

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String currentPath = parentPath.isEmpty() ? fieldName : parentPath + "." + fieldName;

            if (regexPatterns.containsKey(currentPath)) {
                String regex = regexPatterns.get(currentPath);
                if (!fieldName.matches(regex)) {
                    System.out.println("Invalid key: " + currentPath);
                    isValid = false;
                }
            }

            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue != null && fieldValue.isValueNode()) { 
                String value = fieldValue.asText();
                String regex = regexPatterns.get(currentPath);
                if (regex != null && !value.matches(regex)) {
                    System.out.println("Invalid value for key " + currentPath + ": " + value);
                    isValid = false;
                }
            }

            if (node.get(fieldName).isObject()) {
                isValid = validateValues(node.get(fieldName), currentPath, regexPatterns) && isValid;
            }

            if (node.get(fieldName).isArray()) {
                int index = 0;
                for (JsonNode arrayElement : node.get(fieldName)) {
                    if (arrayElement.isObject()) {
                        isValid = validateValues(arrayElement, currentPath + "[" + index + "]", regexPatterns) && isValid;
                    }
                    index++;
                }
            }
        }
        return isValid;
    }
}
