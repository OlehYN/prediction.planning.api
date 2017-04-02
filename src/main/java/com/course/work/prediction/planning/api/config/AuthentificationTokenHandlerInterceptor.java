package com.course.work.prediction.planning.api.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.course.work.prediction.planning.api.entity.User;
import com.course.work.prediction.planning.api.service.domain.UserService;
import com.eaio.uuid.UUID;

public class AuthentificationTokenHandlerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Map<String, TokenInfo> tokens;

	private Map<String, GroupEnum[]> availablePathes;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void pathes() {
		
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setExpiretionDate(new Date(System.currentTimeMillis() + System.currentTimeMillis()));
		tokenInfo.setGroupEnum(GroupEnum.PREMIUM_USER);
		tokenInfo.setUserId(4L);
		
		tokens.put("228", tokenInfo);
		
		availablePathes = new HashMap<>();

		availablePathes.put("/", new GroupEnum[] {});
		availablePathes.put("/addExample", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/addFeatureValue", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/createFeature", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/createModel", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/deleteModel", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/examples", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });		
		availablePathes.put("/features", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/list", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/login", new GroupEnum[] {});
		availablePathes.put("/logout", new GroupEnum[] {});
		availablePathes.put("/predict", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/removeExample", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/renameFeature", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		availablePathes.put("/updateModel", new GroupEnum[] { GroupEnum.FREE_USER, GroupEnum.PREMIUM_USER });
		
	}

	private Integer duration = 3 * 24 * 60 * 60 * 1000;

	private void cleanUp() {
		Date currentDate = new Date(System.currentTimeMillis());
		for (Iterator<Entry<String, TokenInfo>> it = tokens.entrySet().iterator(); it.hasNext();) {
			Entry<String, TokenInfo> entry = it.next();
			if (currentDate.after(entry.getValue().getExpiretionDate()))
				it.remove();
		}
	}

	private boolean isValid(TokenInfo tokenInfo, GroupEnum[] pathes) {
		if (tokenInfo == null)
			return false;
		if (pathes == null)
			return false;
		if (new Date(System.currentTimeMillis()).after(tokenInfo.getExpiretionDate()))
			return false;
		for (GroupEnum groupEnum : pathes)
			if (groupEnum.toString().equals(tokenInfo.getGroupEnum().toString()))
				return true;

		return false;
	}

	private GroupEnum getByValue(String groupName) {
		for (GroupEnum e : GroupEnum.values()) {
			if (e.toString() == e.toString()) {
				return e;
			}
		}
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getServletPath();
		GroupEnum[] pathes = availablePathes.get(path);

		if (pathes == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} else if (path.equals("/login")) {
			String login = request.getParameter("login");
			String password = request.getParameter("password");

			if (login == null || password == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			User user = userService.findByName(login);

			if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			UUID uuid = new UUID();
			Date expirationDate = new Date(System.currentTimeMillis() + duration);

			TokenInfo tokenInfo = new TokenInfo(expirationDate, getByValue(user.getGroup().getName()),
					user.getUserId());
			tokens.put(uuid.toString(), tokenInfo);
			response.getWriter().write(uuid.toString());

			cleanUp();

			return false;
		} else if (path.equals("/logout")) {
			if (request.getParameter("token") != null)
				tokens.remove(request.getParameter("token"));
			return false;
		}

		if (pathes.length == 0)
			return true;
		else {
			String token = (String) request.getParameter("token");
			TokenInfo tokenInfo = tokens.get(token);

			if (isValid(tokenInfo, pathes))
				return true;
			else {
				tokens.remove(token);
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
