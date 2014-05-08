import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibIntercepting {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassToBeProxied.class);
        enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (method.getName().endsWith("Two")) {
                System.out.println("before");
                Object retVal = methodProxy.invokeSuper(target, args);
                System.out.println("after");
                return retVal;
                } else {
                return methodProxy.invokeSuper(target, args);
                }
                }
                });
        ClassToBeProxied proxy = (ClassToBeProxied) enhancer.create();

        proxy.toCallOne();
        proxy.toCallTwo();
    }

}
