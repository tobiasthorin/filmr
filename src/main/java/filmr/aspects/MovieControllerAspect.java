package filmr.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MovieControllerAspect {

    @Before("execution(*.* (*))")
    public void test() {
        System.out.println("TEST TEST TEST TEST TEST TEST TEST TEST");
    }

    @Before("execution(* filmr.controllers.*.read*(*))")
    public void getMovieAdvice() {
        System.out.println("MOVIE READ EVERYTHING IS OK NOTHING TO SEE HERE");
    }

    @Before("allMethodsPointcut()")
    public void allControllMethods() {
        System.out.println("PLING PLONG I AM ACTIVE");
    }

    @Pointcut
    public void allMethodsPointcut(){}
}
