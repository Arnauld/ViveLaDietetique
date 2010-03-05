package fr.viveladietetique.shared.rpc;

import static fr.viveladietetique.shared.data.RoleOp.AtLeastOneOf;
import fr.viveladietetique.shared.annotation.RoleGuard;
import fr.viveladietetique.shared.data.Role;
import net.customware.gwt.dispatch.shared.Action;

@RoleGuard(roles={Role.User},op=AtLeastOneOf)
public class SendGreeting implements Action<SendGreetingResult> {

	private static final long serialVersionUID = 5804421607858017477L;

	private String name;

	@SuppressWarnings("unused")
	private SendGreeting() {
	}

	public SendGreeting(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}