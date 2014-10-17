import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickstart {

	private static final transient Logger log = LoggerFactory
			.getLogger(Quickstart.class);

	public static void doLogin(Subject currentUser, String username,
			String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			currentUser.login(token);
			currentUser.getSession().setAttribute("username", username);
		} catch (UnknownAccountException uae) {
			log.info("There is no user with username of " + token.getUsername());
		} catch (IncorrectCredentialsException ice) {
			log.info("Password for account " + token.getUsername()
					+ " was incorrect!");
		} catch (LockedAccountException lae) {
			log.info("The account for username " + token.getUsername()
					+ " is locked.  "
					+ "Please contact your administrator to unlock it.");
		}
		// ... catch more exceptions here
		catch (AuthenticationException ae) {
			// unexpected condition? error?
		}
	}

	public static void showWhatRolesYouAre(Subject currentUser) {
		if (currentUser.hasRole("warrior"))
			log.info("You are a warrior.");
		if (currentUser.hasRole("ranger"))
			log.info("You are a ranger.");
		if (currentUser.hasRole("swordman"))
			log.info("You are a swordman.");
		if (currentUser.hasRole("fighter"))
			log.info("You are a fighter.");
		if (currentUser.hasRole("archer"))
			log.info("You are an archer.");
		if (currentUser.hasRole("mage"))
			log.info("You are a mage.");
	}

	public static void wieldBow(Subject currentUser) {
		if (currentUser.isPermitted("shoot"))
			log.info("You wield a bow.");
		else
			log.info("You can not wield a bow.");
	}

	public static void wieldSword(Subject currentUser) {
		if (currentUser.isPermitted("melee:sword"))
			log.info("You wield a sword.");
		else
			log.info("You can not wield a sword.");
	}

	public static void doShoot(Subject currentUser) {
		if (currentUser.isPermitted("shoot"))
			log.info("You shoot an arrow on an enemy.");
	}

	public static void doCast(Subject currentUser) {
		if (currentUser.isPermitted("cast:fireball"))
			log.info("You cast fireball on an enemy.");
		if (currentUser.isPermitted("cast:blizzard"))
			log.info("You cast blizzard on an enemy.");
		if (currentUser.isPermitted("cast:storm"))
			log.info("You cast storm on an enemy.");
	}

	public static void doMelee(Subject currentUser) {
		if (currentUser.isPermitted("melee:stab"))
			log.info("You stab an enemy.");
		if (currentUser.isPermitted("melee:slash"))
			log.info("You slash an enemy.");
		if (currentUser.isPermitted("melee:punch"))
			log.info("You punch an enemy.");
	}

	public static void beAttacked(Subject currentUser) {
		if (currentUser.isPermitted("dodge"))
			log.info("An enemy attack you but you dodge it!");
		else if (currentUser.isPermitted("parry"))
			log.info("An enemy attack you but you parry it with your weapon!");
		else if (currentUser.isPermitted("heal"))
			log.info("An enemy attack you and you heal yourself!");
		else
			log.info("An enemy attack you and you die.");
	}

	public static void main(String[] args) {

		Factory<SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		// get the currently executing user:
		Subject currentUser = SecurityUtils.getSubject();

		// login and have permissions
		if (!currentUser.isAuthenticated()) {
			doLogin(currentUser, "Newbie", "newbie");
		}

		if (currentUser.getPrincipal() == null)
			return;

		log.info("User [" + currentUser.getSession().getAttribute("username")
				+ "] logged in successfully.");

		showWhatRolesYouAre(currentUser);

		wieldBow(currentUser);

		wieldSword(currentUser);

		doMelee(currentUser);

		doShoot(currentUser);

		doCast(currentUser);

		beAttacked(currentUser);

		// all done - log out!
		currentUser.logout();
	}
}
