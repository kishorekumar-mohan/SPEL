package com.example.date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SpelDate {
	public static void main(String[] args) {
		try {
			File file = new File("C:\\Users\\Kishore kumar\\eclipse-workspace\\Sspel\\src\\main\\resources\\date.json");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(file);
			String jsonDate = jsonNode.get("date").asText();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate inputDate = LocalDate.parse(jsonDate, formatter);

			ExpressionParser parser = new SpelExpressionParser();
			StandardEvaluationContext context = new StandardEvaluationContext();
			context.setVariable("currentDate", LocalDate.now());
			LocalDate currentDate = parser.parseExpression("#currentDate").getValue(context, LocalDate.class);

			String result = !inputDate.isBefore(currentDate) ? "The date is valid." : "The date is invalid. 'Provide a Present or Upcoming Date'.";

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
