package servlet;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

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

public class Assignment5 extends HttpServlet {

    // Other strings
    static String Style = "https://mason.gmu.edu/~dparikh4/assignment5/assignment5.css";
    static String topBodyHTML = "<body>\n" +
            "        <script src=\"https://mason.gmu.edu/~dparikh4/assignment5/assignment5.js\"></script>\n" +
            "\n" +
            "        <!-- Title Area -->\n" +
            "        <div class=\"main-section\">\n" +
            "                <div class=\"a3-subsection\">\n" +
            "                        <h1>\n" +
            "                                SWE432-001 Assignment 5\n" +
            "                        </h1>\n" +
            "                        <h3>\n" +
            "                                Created By:\n" +
            "                                <a href=\"https://mason.gmu.edu/~afuentel/\">\n" +
            "                                        Alexandro Fuentelzas\n" +
            "                                </a>\n" +
            "                                ,&nbsp;\n" +
            "                                <a href=\"https://mason.gmu.edu/~dparikh4/\">\n" +
            "                                        Deval Parikh\n" +
            "                                </a>\n" +
            "                                ,&nbsp;\n" +
            "                                <a href=\"https://mason.gmu.edu/~spolina/\">\n" +
            "                                        Surya Polina\n" +
            "                                </a>\n" +
            "                        </h3>\n" +
            "                </div>\n" +
            "\n" +
            "                <!-- Forms and User Area -->\n" +
            "                <div class=\"a3-subsection\">\n" +
            "\n" +
            "\n" +
            "                        <!-- Variables Table -->\n" +
            "                        <h1>Define Variables</h1>\n" +
            "                        <h3>Define your variables here to be used in the logical predicates below. Type a variable name and click the plus button to add a new variable.</h3>\n" +
            "                        <form class=\"form\" name=\"form0\" method=\"post\" action=\"\">\n" +
            "                                <table class=\"table-form\" id=\"var_table\">\n" +
            "                                        <tbody><tr>\n" +
            "                                                <th>Variable</th>\n" +

            "\n" +
            "                                                <th><button type=\"button\" class=\"fa fa-plus button\" style=\"font-size:24px\" onclick=\"addVarRow()\"></button></th>\n" +
            "                                        </tr>\n" +
            "                                        <tr onmouseover=\"var_table.clickedRowIndex=this.rowIndex\">\n" +
            "                                                <td><input type=\"text\" name=\"defined_variable_1[]\" id=\"defined_variable_1\"></td>\n" +
            "                                        </tr>\n" +
            "\n" +
            "                                </tbody></table>\n" +
            "                                <p class=\"error-text\" id=\"varErrorMessage\"></p>\n" +
            "\n" +
            "\n" +
            "                        </form>\n" +
            "\n" +
            "                        <!-- Predicates Table -->\n" +
            "                        <h1>Logical Predicates</h1>\n" +
            "                        <h3>Create your logical predicates here. For boolean variables, you can either choose a default boolean value or select from your defined variables. The second logical operator is to link together the next logical predicate. Click the plus button to add more logical predicates. Once you are finished adding predicates, click the Submit button to obtain your results.</h3>\n" +
            "                        <form class=\"form\" method=\"post\" action=\"./assignment5\">\n" +
            "                                <table class=\"table-form\" id=\"logic_table\">\n" +
            "                                        <tbody><tr>\n" +
            "                                                <th>Boolean Variable 1</th>\n" +
            "                                                <th>Logical Operator</th>\n" +
            "                                                <th>Boolean Variable 2</th>\n" +
            "                                                <th>Logical Operator</th>\n" +
            "                                                <th><button type=\"button\" class=\"fa fa-plus button\" style=\"font-size:24px\" onclick=\"addPredRow()\"></button></th>\n" +
            "                                        </tr>\n" +
            "                                        <tr onmouseover=\"logic_table.clickedRowIndex=this.rowIndex\">\n" +
            "                                                <td>\n" +
            "                                                        <select type=\"text\" name=\"Boolean1_1[]\" id=\"ops1_1\">\n" +
            "                                                                <option value=\"true\">True</option>\n" +
            "                                                                <option value=\"false\">False</option>\n" +
            "                                                        </select>\n" +
            "                                                </td>\n" +
            "                                                <td>\n" +
            "                                                        <select type=\"text\" name=\"LogicOp1_1[]\" id=\"LogicOp1_1\">\n" +
            "                                                                <optgroup label=\"Logical Operators\">\n" +
            "                                                                        <option value=\"logic-and\">&amp;&amp; - AND</option>\n" +
            "                                                                        <option value=\"logic-or\">|| - OR</option>\n" +
            "                                                                        <option value=\"logic-xor\">^ - XOR</option>\n" +
            "                                                                </optgroup>\n" +
            "                                                                <optgroup label=\"Bitwise Operators\">\n" +
            "                                                                        <option value=\"bitwise-and\">&amp; - AND</option>\n" +
            "                                                                        <option value=\"bitwise-or\">| - OR</option>\n" +
            "                                                                        <option value=\"bitwise-xor\">^ - XOR</option>\n" +
            "                                                                </optgroup>\n" +
            "                                                        </select>\n" +
            "\n" +
            "                                                </td>\n" +
            "                                                <td>\n" +
            "                                                        <select type=\"text\" name=\"Boolean2_1[]\" id=\"ops2_1\">\n" +
            "                                                                <option value=\"true\">True</option>\n" +
            "                                                                <option value=\"false\">False</option>\n" +
            "                                                        </select>\n" +
            "                                                </td>\n" +
            "                                                <td>\n" +
            "                                                        <select type=\"text\" name=\"LogicOp2_1[]\" id=\"LogicOp2_1\">\n" +
            "                                                                <option value=\"none\">None</option>\n" +
            "                                                                \n" +
            "                                                                <optgroup label=\"Logical Operators\">\n" +
            "                                                                        <option value=\"logic-and\">&amp;&amp; - AND</option>\n" +
            "                                                                        <option value=\"logic-or\">|| - OR</option>\n" +
            "                                                                        <option value=\"logic-xor\">^ - XOR</option>\n" +
            "                                                                </optgroup>\n" +
            "                                                                <optgroup label=\"Bitwise Operators\">\n" +
            "                                                                        <option value=\"bitwise-and\">&amp; - AND</option>\n" +
            "                                                                        <option value=\"bitwise-or\">| - OR</option>\n" +
            "                                                                        <option value=\"bitwise-xor\">^ - XOR</option>\n" +
            "                                                                </optgroup>\n" +
            "                                                        </select>\n" +
            "\n" +
            "                                                </td>\n" +
            "                                                <td><button type=\"button\" class=\"fa fa-trash button\" style=\"font-size:24px\" onclick=\"delPredRow()\"></button></td>\n" +
            "                                        </tr>\n" +
            "\n" +
            "                                </tbody></table>\n" +
            "                                <br>\n" +
            "                                <input class=\"submit-button button\" type=\"submit\" name=\"Submit\" value=\"Submit\">\n" +
            "                        </form>\n";

    static String endBodyHTML = "\n" +
            "                        <h1>Collaboration Summary</h1>\n" +
            "                        <h3>(UPDATE FOR ASSIGNMENT 5) Our group collaboration to complete this assignment. We all contributed to each other's parts. Deval contributed by creating Javascript functions to add and remove logical predicates. Alex contributed by creating Javascript functions to add and remove variables. Surya worked on HTML and CSS of the page. We collaborated together to design the overall usability, layout, and format of the user interface. Alex came up with the variable definition design structure. Surya performed usability testing to help create helpful instructions. Deval wrote instructions.\n" +
            "                        </h3>\n" +
            "                </div>\n" +
            "        </div>\n";


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // HTTP POST request backend logic

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Map formData = request.getParameterMap();

        PrintHead(out);
        PrintPostBody(out, formData);
        PrintTail(out);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        PrintHead(out);
        PrintBody(out);
        PrintTail(out);

        out.flush();
        out.close();

    }



    // HTML functions for doGet



    private void PrintHead(PrintWriter out) {
        out.println("<html>");
        out.println("");

        out.println("<head>");
        out.println("<title>Assignment 5</title>");
        // Import CSS
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
        out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");
        out.println("</head>");
        out.println("");
    }

    private void PrintBody(PrintWriter out) {
        out.println(topBodyHTML);
        out.println(endBodyHTML);
    }

    private void PrintTail(PrintWriter out) {
        out.println("");
        out.println("</html>");
    }



    // HTML functions for doPost


    // HTML Body from doPost
    private void PrintPostBody(PrintWriter out, Map formData) {
        out.println(topBodyHTML);
        PrintUserPredicates(out, formData);
        out.println(endBodyHTML);
    }

    // Helper to generate HTML for user inputted predicates
    private void PrintUserPredicates(PrintWriter out, Map formData) {
        String predicateOutputHTML = "\n" +
                "                        <h1>Final Predicate</h1>\n" +
                "                        <h3>This is your inputted predicate put together! We are using the symbol" +
                " notation for logical operators for clarity and consistency because it makes no difference in regards to a truth table.</h3>\n";
        out.println(predicateOutputHTML);
        out.println("<div class=\"predicates\">");


        Set s = formData.entrySet();
        Iterator it = s.iterator();
        int count = 1;

        while(it.hasNext()){

            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();

//            out.println("Key is "+key+"<br>");
//            if(value.length>1){
//                out.println("test");
//                for (int i = 0; i < value.length; i++) {
//                    out.println("<li>" + value[i].toString() + "</li><br>");
//                }
//            }else
//                out.println("Value is "+value[0].toString()+"<br>");


            if(key.contains("Logic")) {
                String cur_op = value[0];
                // Check if last element and a logical operator selected (formData.size()-1 to ignore the submit button)
                if(!cur_op.equals("none") && count % 4 == 0 && formData.size()-1 == count) {
                    // Ignore selected logical operator
                    continue;
                }
                if(cur_op.contains("and")) {
                    out.println("& ");
                } else if(cur_op.contains("xor")) {
                    out.println("^ ");
                } else if(cur_op.contains("or")) {
                    out.println("| ");
                } else {
                    if(count % 4 != 0) {
                        out.println("ERROR");
                    }
                }

            } else if(key.contains("Bool")) {
                out.println(value[0]+" ");
            }


            count++;
        }
        out.println("</div>");

    }


}
