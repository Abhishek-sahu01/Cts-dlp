package com.dataloader_Login.annotation;

import javax.validation.Payload;

public @interface ValidPassword {
	String message() default "Invalid Password";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};	
}
