package fr.viveladietetique.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.viveladietetique.shared.data.Role;
import fr.viveladietetique.shared.data.RoleOp;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RoleGuard {
	Role[] roles() default {};
	RoleOp op()  default RoleOp.AtLeastOneOf;
	
}
