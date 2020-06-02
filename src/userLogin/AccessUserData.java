package userLogin;

public class AccessUserData {
	
	// Attributes
		private static String username = "user";
		private static String password = "user";
		
		// Methods
		public static String getUserrname() {
			return username;
		}
		
		public static void setUsername(String name) {
			username = name;
		}
		
		public static void setPassword(String pswd) {
			password = pswd;
		}
		
		public static boolean checkUsername(String name) {
			if (name.equals(username)) {
				return true;
			}
			return false;
		}
		
		public static boolean checkUserPassword(String pswd) {
			if (pswd.equals(password)) {
				return true;
			}
			return false;
		}
	}



