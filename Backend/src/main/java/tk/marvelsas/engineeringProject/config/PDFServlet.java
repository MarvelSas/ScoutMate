package tk.marvelsas.engineeringProject.config;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/pdf/*")
public class PDFServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = new File("D:\\Project\\Prywatne\\ScoutMate\\ScoutMate\\Backend\\src\\main\\resources\\pdf\\", filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
