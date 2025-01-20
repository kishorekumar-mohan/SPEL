package com.example.services;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ValidationResult {

    public static void main(String[] args) throws Exception {
        File jsonFile = new File("C:\\Users\\Kishore kumar\\eclipse-workspace\\spel\\src\\suma.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonFile);

        String expression = "#name == 'Kishore' && #age > 21";

        boolean isValid = validateJsonUsingSpEL(rootNode, expression);

        if (isValid) {
            System.out.println("JSON is valid based on the SpEL expression.");
        } else {
            System.out.println("JSON is invalid based on the SpEL expression.");
        }
    }

    public static boolean validateJsonUsingSpEL(JsonNode rootNode, String expression) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        String name = rootNode.has("name") ? rootNode.get("name").asText() : null;
        Integer age = rootNode.has("age") ? rootNode.get("age").asInt() : null;

        context.setVariable("name", name);
        context.setVariable("age", age);

        Boolean result = parser.parseExpression(expression).getValue(context, Boolean.class);

        return result != null && result;
    }

	public void setValid(boolean empty) {
		// TODO Auto-generated method stub
		
	}

	public void setErrors(Set<ValidationMessage> errors) {
		// TODO Auto-generated method stub
		
	}

	public void setExtraFields(List<String> extraFields) {
		// TODO Auto-generated method stub
		
	}
}
