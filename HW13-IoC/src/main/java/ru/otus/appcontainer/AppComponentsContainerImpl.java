package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    private static Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass){
        try {
            processConfig(initialConfigClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) throws Exception {
        var sortedConf = getConfigClassList(Arrays.stream(initialConfigClasses));
        for (Class<?> confClass : sortedConf) {
            processConfig(confClass);
        }
    }

    public AppComponentsContainerImpl(String namePackage) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(namePackage)));
        var allConfClass = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        var sortedConf = getConfigClassList(allConfClass.stream());
        for (Class<?> confClass : sortedConf) {
            processConfig(confClass);
        }
    }

    private List<Class<?>> getConfigClassList(Stream<Class<?>> stream){
        return stream.sorted(Comparator.comparingInt(config -> config.getAnnotation(AppComponentsContainerConfig.class).order()))
                .collect(Collectors.toList());
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);

        var constructor = configClass.getConstructor();
        var objInstance = constructor.newInstance();
        var methods = getAllConfigMethods(configClass.getDeclaredMethods());
        for (Method method : methods) {
            var allParams = Arrays.stream(method.getParameters())
                    .map(parameter -> getAppComponent(parameter.getType()))
                    .collect(Collectors.toList());
            var object = method.invoke(objInstance, allParams.toArray());
            appComponents.add(object);
            appComponentsByName.put(method.getName(), object);
        }
    }

    private List<Method> getAllConfigMethods(Method[] declaredMethods) {
        return Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(c -> c.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (var component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
