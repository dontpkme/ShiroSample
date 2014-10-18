package net.dpkm.shiro.realm;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomizeRealm extends AuthorizingRealm {

	private HashMap<String, String> pwdTable = new HashMap<String, String>();
	private HashMap<String, ArrayList<String>> userRoleTable = new HashMap<String, ArrayList<String>>();
	private HashMap<String, ArrayList<String>> rolePermTable = new HashMap<String, ArrayList<String>>();
	
	private void initUserRole(String user, String... roles) {
		ArrayList<String> userRoles = new ArrayList<String>();
		for(String role: roles) {
			userRoles.add(role);
		}
		userRoleTable.put(user, userRoles);
	}
	
	private void initRolePerm(String role, String... perms) {
		ArrayList<String> rolePerms = new ArrayList<String>();
		for(String perm: perms) {
			rolePerms.add(perm);
		}
		rolePermTable.put(role, rolePerms);
	}
	
    public CustomizeRealm() {
    	// [users]
		pwdTable.put("Joe", "joe");
		pwdTable.put("Kirin", "kirin");
		pwdTable.put("Jimmy", "jimmy");
		pwdTable.put("Study", "study");
		pwdTable.put("Asip", "asip");
		pwdTable.put("Newbie", "newbie");
		
		initUserRole("Joe", "warrior");
		initUserRole("Asip", "ranger");
		initUserRole("Kirin", "swordman", "fighter");
		initUserRole("Jimmy", "archer");
		initUserRole("Study", "mage");
		initUserRole("Newbie");
		
		// [roles]
		initRolePerm("warrior", "melee:*");
		initRolePerm("ranger", "melee:stab", "dodge");
		initRolePerm("swordman", "melee:slash", "parry");
		initRolePerm("fighter", "melee:punch", "dodge");
		initRolePerm("archer", "shoot");
		initRolePerm("mage", "cast:fireball,blizzard", "heal");
	}

    protected SimpleAccount getAccount(String username) {
        SimpleAccount account = new SimpleAccount(username, pwdTable.get(username), getName());
        ArrayList<String> userRoles = userRoleTable.get(username);
        for(String role: userRoles) {
        	account.addRole(role);
        	account.addStringPermissions(rolePermTable.get(role));
        }
        return account;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
    	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return getAccount(upToken.getUsername());
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        return getAccount(username);
    }
}
