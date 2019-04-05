package myFrame.frame.core;

import myFrame.frame.annotaion.web.ResponseBody;
import myFrame.frame.core.json.JsonSerializer;
import myFrame.frame.core.process.BeanCoreFactory;
import myFrame.frame.core.process.MapController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends HttpServlet {

    private MapController mapRouter;

    private JsonSerializer jsonSerializer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        jsonSerializer = new JsonSerializer();

        String scanPackage = config.getInitParameter("scanPackage");
        Application application = new Application();
        application.init(scanPackage);

        mapRouter = BeanCoreFactory.getControllerContainer();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dispatch(request, response);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        String url = req.getContextPath() + req.getRequestURI();
        Object obj = mapRouter.getController(url);
        Method method = obj.getClass().getMethod(mapRouter.getMethodName(url));
        Object result = method.invoke(obj);

        String body = result.toString();
        if (method.isAnnotationPresent(ResponseBody.class))
            body = jsonSerializer.toJson(result);

        resp.getOutputStream().write(body.getBytes());
    }
}
