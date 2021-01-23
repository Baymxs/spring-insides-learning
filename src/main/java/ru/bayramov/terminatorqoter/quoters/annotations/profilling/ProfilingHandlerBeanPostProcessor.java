package ru.bayramov.terminatorqoter.quoters.annotations.profilling;

import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.bayramov.terminatorqoter.quoters.annotations.profilling.Profiling;
import ru.bayramov.terminatorqoter.quoters.annotations.profilling.ProfilingController;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class<?>> map = new HashMap<>();

    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(Profiling.class)) {
            System.out.println(beanClass);
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> beanClass = map.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                if (controller.isEnabled()) {
                    System.out.println("Профилирую метод [" + method.getName() + "]");

                    long before = System.nanoTime();
                    Object retVal = method.invoke(bean, args);
                    long after = System.nanoTime();

                    System.out.println("Профилировка окончена: " + (after - before));

                    return retVal;
                } else {
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
