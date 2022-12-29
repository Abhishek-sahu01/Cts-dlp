package com.dataloader_Login.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataloader_Login.annotation.ValidPassword;

import lombok.SneakyThrows;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	private static final Logger log = LoggerFactory.getLogger(PasswordConstraintValidator.class);

	@Override
	public void initialize(final ValidPassword constraintAnnotation) {
		log.info("START");
		log.info("END");

	}

	@SneakyThrows
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info("START");
		Properties props = new Properties();
		log.debug("Props {}", props);
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");
		log.debug("Input Stream {}", inputStream);

		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageResolver resolver = new PropertiesMessageResolver(props);
		log.debug("Input Stream {}", inputStream);
		PasswordValidator validator = new PasswordValidator(resolver,
				Arrays.asList(new LengthRule(8, 20), new CharacterRule(EnglishCharacterData.UpperCase, 1),
						new CharacterRule(EnglishCharacterData.LowerCase, 1),
						new CharacterRule(EnglishCharacterData.Digit, 1),
						new CharacterRule(EnglishCharacterData.Special, 1), new WhitespaceRule()));
		log.debug("Password Validator {}", validator);
		RuleResult result = validator.validate(new PasswordData(value));
		log.debug("RuleResult {}", result);
		if (result.isValid()) {
			return true;
		}
		List<String> messages = validator.getMessages(result);
		log.debug("Messages {}",messages);
		String messageTemplate = String.join(",", messages);
		log.debug("Message Template {}",messageTemplate);
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		log.info("END");
		return false;
	}

}
