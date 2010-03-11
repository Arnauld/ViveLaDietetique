package fr.viveladietetique.server.core.impl;

import fr.viveladietetique.server.core.AppUser;
import fr.viveladietetique.server.core.AppUserDao;
import fr.viveladietetique.util.EnhancedList;
import fr.viveladietetique.util.StringUtils;
import fr.viveladietetique.util.F1.ToBoolean;

public class AppUserDaoMemImpl implements AppUserDao {
	
	private EnhancedList<AppUser> users = EnhancedList.arrayList();
	
	public AppUserDaoMemImpl() {
	}
	
	@Override
	public AppUser findByLogin(String login) {
		return users.select(whereLoginEquals(login));
	}

	private ToBoolean<AppUser> whereLoginEquals(final String login) {
		return new ToBoolean<AppUser>() {
			@Override
			public boolean op(AppUser user) {
				return StringUtils.equals(user.getEmail(), login);
			}
		};
	}

	public AppUserDaoMemImpl initDefaults() {
		users
			.getUnderlying()
			.add(new AppUser()
				.withEmail("a@no.mail")
				.withPassword("proteine"));
		return this;
	}

}
