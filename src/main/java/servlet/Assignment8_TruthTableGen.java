package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet(
        name = "/assignment8_TruthTableGen",
        urlPatterns = {"/assignment8_TruthTableGen"}
)

public class Assignment8_TruthTableGen extends HttpServlet {

    // Other strings
    static String Style = Assignment8.Style;

    static String endBodyHTML = Assignment8.endBodyHTML;


    private String FILEPATH = "predicates.json";

    List<Map<String, String[]>> allFormData = new ArrayList();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // HTTP POST request backend logic

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Map formData = request.getParameterMap();

        // Add new formData to list of allFormData
        allFormData = getAllPredicates();
        allFormData.add(formData);

        // Write form data -> json to file
        try{
            // Open JSON file
            FileWriter fileWriter = new FileWriter(FILEPATH);
            // Create JSON from list of allFormData and write to JSON file
            new Gson().toJson(allFormData, fileWriter);

            fileWriter.flush();
            fileWriter.close();
        }catch(IOException ioException){}

        PrintHead(out);
        PrintPostBody(out, formData);
        PrintTail(out);
    }


    // Functions for Storing Predicates

    // Get all predicates formData
    private List<Map<String, String[]>> getAllPredicates() {
        List<Map<String, String[]>> allPredicates = new ArrayList();
        try{
            File file = new File(FILEPATH);
            if(!file.exists()){
                return allPredicates;
            }

            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(file));
            List<Map<String, String[]>> readEntries =
                    new Gson().fromJson(bufferedReader, new TypeToken<List<Map<String, String[]>>>(){}.getType());

            if(readEntries != null){
                allPredicates = readEntries;
            }
            bufferedReader.close();

        }catch(IOException ioException){
        }
        return allPredicates;
    }



    // HTML functions for doGet


    private void PrintHead(PrintWriter out) {
        out.println("<html>");
        out.println("");

        out.println("<head>");
        out.println("<title>Assignment 8</title>");
        // Import CSS
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
        out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");
        out.println("</head>");
        out.println("");
    }


    private void PrintTail(PrintWriter out) {
        out.println("");
        out.println("</html>");
    }



    // HTML functions for doPost


    // HTML Body from doPost
    private void PrintPostBody(PrintWriter out, Map formData) {
        out.println("<body>\n" +
                "        <script src=\"https://mason.gmu.edu/~dparikh4/assignment5/assignment5.js\"></script>\n" +
                "\n" +
                "        <!-- Title Area -->\n" +
                "        <div class=\"main-section\">\n" +
                "                <div class=\"a3-subsection\">\n" +
                "                        <h1>\n" +
                "                                SWE432-001 Assignment 8\n" +
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
                "                <div class=\"a3-subsection\">\n");
        String predicateOutputBlurbHTML = "\n" +
                "                        <h1>Final Predicate</h1>\n" +
                "                        <h3>This is your inputted predicate put together! We are using the symbol" +
                " notation for logical operators for clarity and consistency because it makes no difference in regards to a truth table.</h3>\n";
        out.println(predicateOutputBlurbHTML);

        String[] predItems = PrintUserPredicates(out, formData);
        printTruthTable(out, predItems);
        out.println(endBodyHTML);
    }

    // Helper to generate HTML for user inputted predicates
    private String[] PrintUserPredicates(PrintWriter out, Map formData) {

        out.println("<div class=\"predicates\">");

        Set s = formData.entrySet();
        Iterator it = s.iterator();
        int count = 1;

        String[] predItems = new String[formData.size()-1];

        while(it.hasNext()){

            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();

            if(key.contains("Logic")) {
                String cur_op = value[0];
                // Check if last element and a logical operator selected (formData.size()-1 to ignore the submit button)
                if(!cur_op.equals("none") && count % 4 == 0 && formData.size()-1 == count) {
                    // Ignore selected logical operator
                    continue;
                }
                if(cur_op.contains("and")) {
                    predItems[count-1] = "and";
                    out.println("& ");
                } else if(cur_op.contains("xor")) {
                    predItems[count-1] = "xor";
                    out.println("^ ");
                } else if(cur_op.contains("or")) {
                    predItems[count-1] = "or";
                    out.println("| ");
                } else {
                    if(count % 4 != 0) {
                        out.println("ERROR");
                    }
                }

            } else if(key.contains("Bool")) {
                predItems[count-1] = value[0];
                out.println(value[0]+" ");
            }

            count++;
        }
        out.println("</div>");
        return predItems;
    }

    private void printTruthTable(PrintWriter out, String[] predItems) {

        // Give each unique variable ids to be used as indices for boolean values
        Map<String, Integer> varIds = new HashMap<>();

        int varId = 0;
        for(int i = 0; i < predItems.length; i+=2) {
            String curVar = predItems[i];
            if(!varIds.containsKey(curVar)) {
                varIds.put(curVar, varId);
                varId++;
            }
        }


        int rows = (int) Math.pow(2, varId);

        //  --------
        // | p | q |  <-- variables
        //  --------

        // boolVals:
        //  --------
        // | 0 | 0 |
        //  --------
        // | 0 | 1 |
        //  --------
        // | 1 | 0 |
        //  --------
        // | 1 | 1 |
        //  --------

        int[][] boolVals = new int[rows][varId]; // Each Row is the value of bool vars, each col is a boolvar


        // Builds truth table (variable side)
        for (int i=0; i<rows; i++) {
            for (int j=varId-1; j>=0; j--) {
                boolVals[i][varId-1 - j] = (i/(int) Math.pow(2, j))%2;
            }
        }

        // Prints truth table (variable side)
//        for(int i = 0; i < boolVals.length; i++) {
//            for(int j = 0; j < boolVals[i].length; j++) {
//                System.out.print(boolVals[i][j] + " ");
//            }
//            System.out.println("next");
//        }

        // Build truth table (result side)
        int[] ttresults = new int[rows];

        // Go through each row (possibilities of values)
        for(int i = 0; i < boolVals.length; i++) {

            // boolean variable values. Variable IDs represent the index of this array
            int[] curBoolVarValues = boolVals[i];

            // Go through predicate and calculate result for current array of values
            int curResult = 0;
            int prevResult = 0;
            for(int j = 0; j < predItems.length-2; j += 2) {
                int tmpResult = 0;

                // First boolean variable is either first predicate item or previously built up value
                int curBoolVar1;
                if(j == 0) {
                    curBoolVar1 = curBoolVarValues[varIds.get(predItems[j])];
                } else {
                    curBoolVar1 = prevResult;
                }

                // Current operation to do between curBoolVar1 and curBoolVar2
                String curOp = predItems[j+1];

                // Second boolean variable
                int curBoolVar2 = curBoolVarValues[varIds.get(predItems[j+2])];


                // Do actual operation
                if(curOp.equals("and")) {
                    tmpResult = curBoolVar1 & curBoolVar2;
                } else if(curOp.equals("xor")) {
                    tmpResult = curBoolVar1 ^ curBoolVar2;
                } else if(curOp.equals("or")) {
                    tmpResult = curBoolVar1 | curBoolVar2;
                }

                // Set prevResult to tmpResult to continue operation
                prevResult = tmpResult;
            }
            curResult = prevResult;
            ttresults[i] = curResult;
        }

        // Merge two parts of the truth table
        int[][] truthTable = new int[rows][varId+1];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < boolVals[i].length; j++) {
                truthTable[i][j] = boolVals[i][j];
            }
            truthTable[i][truthTable[i].length-1] = ttresults[i];
        }

        // Output table
        out.println("<table class=\"result-table\">");

        // Title row
        out.println("<tr>");
        out.println("<td>row</td>");
        Set<String> names = new HashSet<>();
        for(int i = 0; i <  predItems.length; i+=2) {
            if(!names.contains(predItems[i])) {
                out.println("<td>" + predItems[i] + "</td>");
                names.add(predItems[i]);
            }
        }
        out.println("<td>result</td>");
        out.println("</td>");
        out.println("</tr>");

        for(int i = 0; i < truthTable.length; i++) {
            out.println("<tr>");
            int rownum = i+1; // only used to show on table
            out.println("<td>" + rownum + "</td>");

            for(int j = 0; j < truthTable[i].length; j++) {
                out.println("<td>" + truthTable[i][j] + "</td>");
                System.out.print(truthTable[i][j] + " ");
            }
            out.println("</tr>");

            System.out.println("next");
        }

        out.println("</table>");


    }


}
