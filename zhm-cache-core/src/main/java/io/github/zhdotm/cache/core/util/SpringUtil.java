package io.github.zhdotm.cache.core.util;


import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * spring util
 *
 * @author zhihao.mao
 */

public class SpringUtil implements ApplicationContextAware, BeanFactoryAware {

    private static ApplicationContext applicationContext;

    private static ConfigurableListableBeanFactory beanFactory;

    public static <T> T getBean(Class<T> clazz) {

        return applicationContext.getBean(clazz);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {

        return applicationContext.getBeansOfType(clazz);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotation) {

        return applicationContext.getBeansWithAnnotation(annotation);
    }

    public static void registerSingleton(String beanName, Object bean) {

        beanFactory.registerSingleton(beanName, bean);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

}
