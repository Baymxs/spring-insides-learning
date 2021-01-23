package ru.bayramov.terminatorqoter.quoters.annotations.injectrandomint;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import ru.bayramov.terminatorqoter.quoters.annotations.injectrandomint.InjectRandomInt;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInt injectRandomInt = field.getAnnotation(InjectRandomInt.class);

            if (injectRandomInt != null) {
                int min = injectRandomInt.min();
                int max = injectRandomInt.max();

                Random random = new Random();
                int i = min + random.nextInt(max-min);

                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, i);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
