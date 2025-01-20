package com.example.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.File;
import java.util.Iterator;
 

public class SpelSer {
 
	public static void main(String[] args) throws Exception {
        File jsonFile = new File("C:\\Users\\Kishore kumar\\eclipse-workspace\\spel\\src\\listInput.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonFile);

        String regexPattern = "^[a-zA-Z0-9_]+$";
  
        boolean isValid = validateRegex(rootNode, regexPattern);

        if (isValid) {
            System.out.println("All the JSON keys are valid based on the regex pattern.");
        } else {
            System.out.println("Some of JSON keys are invalid based on the regex pattern.");
        }
    }
	
	

    public static boolean validateRegex(JsonNode rootNode, String regexPattern) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        return validateKey(rootNode, regexPattern, parser, context);
    }

    private static boolean validateKey(JsonNode node, String regexPattern, ExpressionParser parser, StandardEvaluationContext context) {
        Iterator<String> fieldNames = node.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            context.setVariable("key", fieldName);
            Boolean isValid = parser.parseExpression("#key matches '" + regexPattern + "'").getValue(context, Boolean.class);

            if (Boolean.FALSE.equals(isValid)) {
                System.out.println("Invalid key: " + fieldName);
                return false; 
            }

            if (node.get(fieldName).isObject()) {
                boolean nestedValid = validateKey(node.get(fieldName), regexPattern, parser, context);
                if (!nestedValid) return false;
            }

            if (node.get(fieldName).isArray()) {
                for (JsonNode arrayElement : node.get(fieldName)) {
                    if (arrayElement.isObject()) {
                        boolean arrayValid = validateKey(arrayElement, regexPattern, parser, context);
                        if (!arrayValid) return false;
                    }
                }
            }
        }

        return true; 
    }
}
