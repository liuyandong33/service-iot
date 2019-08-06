package build.dream.iot.listeners;

import build.dream.common.listeners.BasicServletContextListener;
import build.dream.common.mappers.CommonMapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class IotServletContextListener extends BasicServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        previousInjectionBean(servletContextEvent.getServletContext(), CommonMapper.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }
}
