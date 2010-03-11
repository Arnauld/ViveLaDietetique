package fr.viveladietetique.shared.data;

import java.util.Arrays;
import java.util.Set;

public enum RoleOp {
	AtLeastOneOf,
	AllOf,
	NoneOf;
	
	public Matcher newMatcher() {
		switch(this) {
			case AllOf : {
				return new Matcher() {
					@Override
					protected boolean isFullfilled(RoleSet availables, Iterable<Role> guardedBy) {
						return availables.allOf(guardedBy);
					}
				};
			}
			case AtLeastOneOf : {
				return new Matcher() {
					@Override
					protected boolean isFullfilled(RoleSet availables, Iterable<Role> guardedBy) {
						return availables.oneOf(guardedBy);
					}
				};
			}
			case NoneOf : {
				return new Matcher() {
					@Override
					protected boolean isFullfilled(RoleSet availables, Iterable<Role> guardedBy) {
						return availables.noneOf(guardedBy);
					}
				};
			}
			default : {
				throw new IllegalStateException("No matcher found for type <"+this+">");
			}
		}
	}
	
	public static abstract class Matcher {
		private RoleSet availables;
		private Iterable<Role> guardedBy;
		public Matcher availables(RoleSet availables) {
			this.availables = availables;
			return this;
		}
		public Matcher availables(Set<Role> availables) {
			return availables(RoleSet.wrap(availables));
		}
		public Matcher guardedBy(Role...guardedBy) {
			this.guardedBy = Arrays.asList(guardedBy);
			return this;
		}
		public boolean isFullfilled() {
			return isFullfilled(availables, guardedBy);
		}
		protected abstract boolean isFullfilled(RoleSet availables, Iterable<Role> guardedBy);
	}
}
