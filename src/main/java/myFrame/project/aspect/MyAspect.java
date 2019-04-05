package myFrame.project.aspect;

import myFrame.frame.annotaion.aspect.Aspect;
import myFrame.frame.annotaion.aspect.Before;

@Aspect
public class MyAspect {

    @Before("myFrame.project.service.MyService.getPerson")
    public void before() {
        System.out.println("before");
    }
}
