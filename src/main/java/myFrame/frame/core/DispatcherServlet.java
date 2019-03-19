package myFrame.frame.core;

import myFrame.Main;
import myFrame.frame.annotaion.web.ResponseBody;
import myFrame.frame.core.json.JsonSerializer;
import myFrame.frame.core.process.MapRouter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<String, String> mapRoute;
    private MapRouter router;

    private JsonSerializer jsonSerializer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        jsonSerializer = new JsonSerializer();

        Application application = new Application(Main.class);
        application.init();

        mapRoute = application.getMapRouter().getMap();
        router = application.getMapRouter();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dispatch(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getOutputStream().write("server error".getBytes());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String url = req.getContextPath() + req.getRequestURI();
        Object obj = router.getController(url);
        Method method = obj.getClass().getMethod(mapRoute.get(url));
        Object result = method.invoke(obj);

        String body = result.toString();
        if (method.isAnnotationPresent(ResponseBody.class))
            body = jsonSerializer.toJson(result);

        resp.getOutputStream().write(body.getBytes());
    }
}
