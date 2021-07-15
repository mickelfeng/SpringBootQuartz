package com.godfunc.quartz;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.godfunc.entity.JobEntity;
import com.godfunc.utils.SpringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobInvokeUtils {

    public static Object invoke(JobEntity job) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        // com.godfunc.task.TestTask#up(1L, "12")
        // testTask.up(2L, "12")
        String invoke = job.getInvoke();
        if (invoke.contains("#")) {
            int classIndex = invoke.indexOf("#");
            int methodIndex = invoke.indexOf("(");
            int paramIndex = invoke.lastIndexOf(")");
            String clazzStr = invoke.substring(0, classIndex);
            String method = invoke.substring(classIndex + 1, methodIndex);
            String paramsStr = invoke.substring(methodIndex + 1, paramIndex);
            Class<?> clazz = null;
            clazz = Class.forName(clazzStr);
            Object bean = clazz.newInstance();
            if (StringUtils.isNotBlank(paramsStr)) {
                Map<Class, Object> params = getParams(paramsStr);
                Method invokMethod = bean.getClass().getDeclaredMethod(method, params.keySet().toArray(new Class[0]));
                return invokMethod.invoke(bean, params.values().toArray());

            }

        } else {
            String beanName = invoke.substring(0, invoke.indexOf("."));
            String method = invoke.substring(invoke.indexOf("."), invoke.indexOf("("));
            Object bean = SpringUtils.getBean(beanName);
            //   bean.getClass().getDeclaredMethod(method, )
        }
        return null;
    }

    public static Class getClass(String target) throws ClassNotFoundException {
        if (target.contains("#")) {
            int classIndex = target.indexOf("#");
            String clazz = target.substring(0, classIndex);
            return Class.forName(clazz);
        } else {
            return null;
        }
    }


    public static Map<Class, Object> getParams(String paramStr) {
        String[] split = paramStr.split(",");
        Map<Class, Object> params = new LinkedHashMap<>(split.length);
        for (String param : split) {
            // String
            param = param.trim();
            if (param.startsWith("\"") && param.endsWith("\"")) {
                params.put(String.class, param.substring(1, param.length() - 1));
            } else if (param.endsWith("L") || param.endsWith("l")) {
                params.put(Long.class, Long.parseLong(param.substring(0, param.length() - 1)));
            } else if (param.endsWith("F") || param.endsWith("f")) {
                params.put(Float.class, Float.parseFloat(param));
            } else if (param.endsWith("D") || param.endsWith("d")) {
                params.put(Double.class, Double.parseDouble(param));
            } else {
                params.put(Integer.class, Integer.parseInt(param));
            }
        }
        return params;
    }
}
