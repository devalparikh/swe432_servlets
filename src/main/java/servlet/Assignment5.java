package servlet;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "/assignment5",
        urlPatterns = {"/assignment5"}
)

public class Assignment5 extends HttpServlet
{
    @Override
    protected void doGet  (HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {

        res.setContentType ("text/html");
        PrintWriter out = res.getWriter ();

        out.println ("<HTML>");
        out.println ("<HEAD>");
        out.println ("<TITLE>A simple servlet program</TITLE>");
        out.println ("</HEAD>");

        out.println ("<BODY>");
        out.println ("<CENTER>");

        out.println ("<B>Hello this is assignment 5!</B>");

        out.println ("</CENTER>");
        out.println ("</BODY>");

        out.println ("</HTML>");
        out.flush();

        out.close ();

    }
}
