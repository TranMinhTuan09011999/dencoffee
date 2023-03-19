package com.manage.services.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class SessionManagementService {

  HttpSession session;

  @Autowired
  SessionRepository sessionRepository;

  public void setAttribute(String attrName, Object attrValue) {
    HttpSession httpSession = this.findSessionByCurrentRequest();
    Session session = sessionRepository.getSession(httpSession.getId());
    if (session == null) {
      httpSession.setAttribute(attrName, attrValue);
    } else {
      session.setAttribute(attrName, attrValue);
      sessionRepository.save(session);
    }
  }

  public HttpSession findSessionByCurrentRequest() {
    if (this.session == null) {
      final ServletRequestAttributes attr =
              (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      return attr.getRequest().getSession(true);
    }
    return this.session;
  }

  public <T> T getAttribute(String attrName, Class<T> clazz) {
    HttpSession httpSession = this.findSessionByCurrentRequest();
    var tmpSessison = this.findAttributeBySessionId(httpSession.getId(), attrName);
    if (tmpSessison == null) {
      return (T) httpSession.getAttribute(attrName);
    }
    return (T) tmpSessison;
  }

  public Object findAttributeBySessionId(String sessionId, String attrName) {
    Session session = sessionRepository.getSession(sessionId);
    if (Objects.isNull(session)) {
      return null;
    }
    return session.getAttribute(attrName);
  }

  public void removeAttribute(String attrName) {
    HttpSession httpSession = this.findSessionByCurrentRequest();
    if (Objects.nonNull(httpSession)) {
      httpSession.removeAttribute(attrName);

      Session session = sessionRepository.getSession(httpSession.getId());
      if (session != null) {
        session.removeAttribute(attrName);
      }
    }
  }

  /**
   * Destroy current Session - Cookies.
   */
  public HttpSession destroy() {
    HttpSession httpSession = this.findSessionByCurrentRequest();
    sessionRepository.delete(httpSession.getId());
    httpSession.invalidate();
    return httpSession;
  }

}
