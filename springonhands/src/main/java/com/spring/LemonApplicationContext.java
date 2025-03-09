package com.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lemoon on 2025/3/2 21:19
 */
public class LemonApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();    //单例池
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    public LemonApplicationContext(Class configClass) {
        this.configClass = configClass;

        //解析配置类
        //ComponentScan注解--->扫描路径 --->扫描--->Beandefinition -->BeanDefinitionMap
        scan(configClass);

        for(Map.Entry<String,BeanDefinition> entry :beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();

            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanDefinition);//单例Bean
                singletonObjects.put(beanName,bean);

            }
        }
    }


    public Object createBean(BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            //依赖注入

            for(Field declaredField : clazz.getDeclaredFields()){
                if(declaredField.isAnnotationPresent(Autowired.class)){

                    //Spring 在给属性赋值时，会根据属性的信息从容器里找一个值
                    Object bean = getBean(declaredField.getName());

                    declaredField.setAccessible(true);
                    declaredField.set(instance,bean);
                    //如果此时单例池里还没有对应的bean怎么办？
                }
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void scan(Class configClass) {
        //在构造方法中做什么事呢？
        //解析配置类
        //ComponentScan注解 -->扫描路径 -->扫描

        ComponentScan componentScanAnnotation = (ComponentScan)configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();  //扫描路径
        path = path.replace(".","/");


        //扫描
        //Bootstrap --->jre/lib
        //Ext --------->jre/ext/lib
        //App --------->classpath--->
        ClassLoader classLoader = LemonApplicationContext.class.getClassLoader();//app
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();

            for(File f: files){
                String fileName = f.getAbsolutePath();

                if(fileName.endsWith(".class")){
                    //取com开头 到.class 结尾中间的
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    String classNameReplace = className.replace("/", ".");
                    System.out.println(classNameReplace);


                    ///Users/lemoon/Documents/IDEA/mygithub/spring-onhands/springonhands/target/classes/com/lemon/service/UserService.class
                    ///Users/lemoon/Documents/IDEA/mygithub/spring-onhands/springonhands/target/classes/com/lemon/service/XxUtil.class
                    //把上面的转化为com.lemon.service.UserService
                    try {
                        Class<?> clazz = classLoader.loadClass(classNameReplace);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            //表示当前这个类是一个Bean
                            //解析类，判断当前bean是单例bean，还是prototype的bean，此时不考虑懒加载了
                            //BeanDefinition

                            //解析类--->BeanDefinition
                            Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnnotation.value();

                            BeanDefinition beanDefinition = new BeanDefinition();

                            if(clazz.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            }else{
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinition.setClazz(clazz);

                            beanDefinitionMap.put(beanName,beanDefinition);

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    public Object getBean(String beanName){
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                Object o = singletonObjects.get(beanName);
                return o;
            }else{
                //创建Bean对象
                Object bean = createBean(beanDefinition);
                return bean;

            }
        }else{
            //不存在对应的Bean
            throw new NullPointerException();
        }
    }

}
