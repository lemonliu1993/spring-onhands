package com.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

/**
 * Created by lemoon on 2025/3/2 21:19
 */
public class LemonApplicationContext {

    private Class configClass;

    public LemonApplicationContext(Class configClass) {
        this.configClass = configClass;

        //在构造方法中做什么事呢？
        //解析配置类
        //ComponentScan注解 -->扫描路径 -->扫描

        ComponentScan componentScanAnnotation = (ComponentScan)configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();  //扫描路径
        System.out.println(path);
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
                System.out.println(f);
                System.out.println(f.getAbsolutePath());
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

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        }

    }

    public Object getBean(String beanName){
        return null;
    }

}
